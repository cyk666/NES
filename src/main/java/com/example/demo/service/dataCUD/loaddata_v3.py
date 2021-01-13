import pandas as pd
import os
import sys
from py2neo import Graph

graph = Graph(password="a12345")



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


# 写cypher语句时调用
def deal_attribute(filename):
    column_names = pd.read_csv(filename, nrows=1).columns.tolist()
    s = ""
    for name in column_names:
        s += "link."+name + "= links." + name + ","
    if s != "":
        s = s[:-1]
    return s
# os.system(cmd)
# if is_new  neo4j-admin
# else batch import
def neo4j_load(node_file_list, rela_file_list, _database_name, _is_new=True):

    # ## filename node_object.csv
    contents = []

    for file in rela_file_list:
        attr = deal_attribute(file)
        filename = file.split("\\")[-1]
        l = filename.split(".")[0].split("_")
        object1 = l[1]
        object2 = l[2]
        rela = "_".join(l[3:])

        if attr != "":
            content = "CALL apoc.periodic.iterate('CALL apoc.load.csv(\""+filename \
                   + "\") yield map as links return links','MATCH(a:" \
                   + object1 + "),(m:" + object2 + ") WHERE a.ID=links.SID AND m.ID=links.EID CREATE (a)-[link:" +\
                   rela+"]->(m) SET " + attr + "', {batchSize:1000, iterateList:true, parallel:true});"
        else:
            content = "CALL apoc.periodic.iterate('CALL apoc.load.csv(\"" + filename \
                      + "\") yield map as links return links','MATCH(a:" \
                      + object1 + "),(m:" + object2 + ") WHERE a.ID=links.SID AND m.ID=links.EID CREATE (a)-[link:" + \
                      rela + "]->(m)" + "', {batchSize:1000, iterateList:true, parallel:true});"
        contents.append(content)
    print(contents)
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

# 文件路径例子：'.\\actor.csv'
list_es, list_neo_node, list_neo_rela = list_file(file_dir)
neo4j_load(list_neo_node, list_neo_rela, is_new)


