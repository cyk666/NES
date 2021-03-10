# coding=gbk
from elasticsearch import Elasticsearch
from elasticsearch import helpers
import pandas as pd
import os
import sys
from py2neo import Graph


es = Elasticsearch()

# return list of es_*.csv ,list of neo4j_*.csv
def list_file(_file_dir):
    es_node_path = []
    es_rela_path = []
    neo_node_path = []
    neo_rela_path = []
    for (dirpath, dirnames, filenames) in os.walk(_file_dir):
        for filename in filenames:
            if filename[0] == 'e':
                if 'node' in filename:
                    es_node_path += [os.path.join(dirpath, filename)]
                else:
                    es_rela_path += [os.path.join(dirpath, filename)]
            elif filename[0] == 'n':

                neo_node_path += [os.path.join(dirpath, filename)]
            else:
                neo_rela_path += [os.path.join(dirpath, filename)]
    return es_node_path,es_rela_path,neo_node_path,neo_rela_path

# csv --> index
def es_import_data(ind, file_name, _index_name):
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


def es_load(list_es_node, list_es_rela, node_index_name, rela_index_name, _is_new=True):
    if _is_new:
        ind = 0
        for file in list_es_node:
            ind = es_import_data(ind, file, node_index_name)
    else:
        ind = es.count(index=node_index_name)['count']
        for file in list_es_node:
            ind = es_import_data(ind, file, node_index_name)

    if _is_new:
        ind = 0
        for file in list_es_rela:
            ind = es_import_data(ind, file, rela_index_name)
    else:
        ind = es.count(index=rela_index_name)['count']
        for file in list_es_rela:
            ind = es_import_data(ind, file, rela_index_name)

def dealFilePath(file):
    filename = file.split("\\")[-1]
    type = filename.split(".")[0].split("_")[-1]
    return filename,type


def neo4j_load(list_neo_node,list_neo_rela,neo4j_database_path,_is_new=True):
    if _is_new:
        s = "neo4j-admin import --database="+neo4j_database_path+" "
        for file in list_neo_node:
            s = s + " --nodes " + '"'+file+'"'+" "
        for file in list_neo_rela:
            s = s + " --relationships " + '"'+file+'"'+" "
        os.system(s)
    else:
        graph = Graph(password="a12345")
        node = []
        rela = []
        for file in list_neo_node:
            filename,label = dealFilePath(file)
            node.append("{fileName:'file:/"+filename+"',labels:['"+label+"']}")
        for file in list_neo_rela:
            filename,type = dealFilePath(file)
            rela.append("{fileName:'file:/"+filename+"',type:'"+type+"'}")
        cmd = "CALL apoc.import.csv(" \
              "["+",".join(node)+"]," \
              "["+",".join(rela)+"]," \
              "{delimiter: ',', stringIds: true});"
        graph.run(cmd)




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
#is_new = False
node_index_name = "node"
rela_index_name = "relationship"
neo4j_database_name = "graph.db"

list_es_node,list_es_rela, list_neo_node, list_neo_rela = list_file(file_dir)
es_load(list_es_node, list_es_rela, node_index_name, rela_index_name,is_new)

neo4j_load(list_neo_node,list_neo_rela,neo4j_database_name,is_new)

