# JanusGraph-ConfiguredGraphFactory
The purpose of this repo is to share a base setup to run a docerized JanusGraph with a ConfiguredGraphFactory. This will bring up a JanusGraph and a Cassandra instances, setup a [template configuration](https://docs.janusgraph.org/basics/configured-graph-factory/#template-configuration) pointing to Cassandra and create a default graph called `default_graph`.

To bring it up run the following commands:
```
$ docker network create jgTestNetwork
$ docker-compose up -d
```

To tear it down:
```
$ docker-compose down
$ docker network rm jgTestNetwork
```

## Connect with Gremlin
Once JanusGraph is running you should be able to connect using Gremlin as always and use `ConfiguredGraphFactory` to mange your graphs on-runtime.


1. Connect to JanusGraph
```
gremlin> :remote connect tinkerpop.server conf/remote.yaml session
==>Configured localhost/127.0.0.1:8182-[0cba27bd-3ad3-44ba-9a97-9226f0490d9a]
gremlin> :remote console
==>All scripts will now be sent to Gremlin Server - [localhost/127.0.0.1:8182]-[0cba27bd-3ad3-44ba-9a97-9226f0490d9a] - type ':remote console' to return to local mode
```

2. Access the default graph
```
gremlin> ConfiguredGraphFactory.getGraphNames()
==>default_graph
gremlin> default_graph_traversal.V().valueMap()
gremlin> default_graph_traversal.addV("Person").property("name","Dona").next()
==>v[4184]
gremlin> default_graph_traversal.V().valueMap()
==>{name=[Dona]}
```

3. Create a new graph configuration
```
gremlin> map = [:]
gremlin> map.put("sotrage.backend", "inmemory")
==>null
gremlin> map.put("graph.graphname", "volatile_graph")
==>null
gremlin> ConfiguredGraphFactory.createConfiguration(new MapConfiguration(map))
gremlin> ConfiguredGraphFactory.getGraphNames()
==>default_graph
==>volatile_graph
gremlin> graph = ConfiguredGraphFactory.open("volatile_graph")
==>standardjanusgraph[inmemory:[127.0.0.1]]
```