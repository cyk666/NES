
from elasticsearch import Elasticsearch
from elasticsearch import helpers
import pandas as pd
import os
import sys
from py2neo import Graph

graph = Graph(password="a12345")
es = Elasticsearch()


# return list of es_*.csv ,list of neo4j_*.csv
def list_file(_file_dir):
    _list_es = []
    _list_neo_node = []
    _list_neo_rela = []
    for file_name in os.listdir(_file_dir):
        file_path = os.path.join(_file_dir, file_name)
        if file_name[0] == 'e':
            _list_es.append(file_path)
        elif file_name[0] == 'n':
            _list_neo_node.append(file_path)
        else:
            _list_neo_rela.append(file_path)
    return _list_es, _list_neo_node, _list_neo_rela


# csv --> index
def import_data(ind, file_name, _index_name):
    actions = []
    df_iterator = pd.read_csv(file_name, chunksize=1000)
    for data in df_iterator:
        columns_name_list = data.columns.values.tolist()
        for index, row in data.iterrows():
            d = {}
            for columns_name in columns_name_list:
                d[columns_name] = row[columns_name]
            action = {"_index": _index_name, "_id": ind, "_source": d}
            ind += 1
            actions.append(action)

        helpers.bulk(es, actions)
        del actions[0:len(actions)]

    return ind


# 自定义的id从0开始
def es_load(file_list, _index_name, _is_new=True):
    if _is_new:
        ind = 0
        for file in file_list:
            ind = import_data(ind, file, _index_name)
    else:
        ind = es.count(index=_index_name)['count']
        for file in file_list:
            ind = import_data(ind, file, _index_name)


def deal_attribute2(filename):
    column_names = pd.read_csv(filename, nrows=1).columns.tolist()
    s = ""
    for name in column_names:
        s += name + ": A." + name + ","
    if s != "":
        s = s[:-1]
    return s
# os.system(cmd)
# if is_new  neo4j-admin
# else batch import
def neo4j_load(node_file_list, rela_file_list, _database_name, _is_new=True):

    ## filename node_object.csv
    content = ""
    contents = []

    for file in node_file_list:

        filename = file.split("\\")[-1]
        ind = filename.split(".")[0].find("_")
        ind2 = filename.find(".")
        subject = filename[ind+1:ind2]

        attr = deal_attribute2(file)
        content = "CALL apoc.periodic.iterate('CALL apoc.load.csv(\"" + filename\
                   + "\") yield map as A return A','MERGE (m:" + subject \
                   + " {" + attr +"}) ', {batchSize:1000, iterateList:true, parallel:true});"
        contents.append(content)


    for content in contents:
        graph.run(content)



def read_sys():
    if len(sys.argv) != 2:
        print("the expected number of argvs is 2 and get "+str(len(sys.argv)))
        print("the example is: python es.py [1表示是增量插入|0表示新创建]")
        exit(0)
    else:
        already_exist = sys.argv[1]
        if already_exist == "1":
            _is_new = False
        else:
            _is_new = True
        return _is_new





file_dir = "F:\\neo4j-community-3.5.5\\import"
is_new = read_sys()
index_name = "test"

# 文件路径例子：'.\\actor.csv'
list_es, list_neo_node, list_neo_rela = list_file(file_dir)
es_load(list_es, index_name, is_new)
neo4j_load(list_neo_node, list_neo_rela, is_new)




