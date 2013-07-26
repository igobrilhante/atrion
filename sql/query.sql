select distinct source,source_lat,source_lng,target,target_lat,target_lng,cost,directed
from
(
select wn1.way_id
,wn1.node_id source,st_x(n1.geom) source_lng,st_y(n1.geom) source_lat
,wn2.node_id target,st_x(n2.geom) target_lng,st_y(n2.geom) target_lat
,wn1.sequence_id,wn2.sequence_id ,st_distance(n1.geom,n2.geom) as cost,w.tags ? 'oneway' as directed
from way_nodes wn1,way_nodes wn2,
nodes n1, nodes n2,ways w,
grid g
where wn1.way_id = wn2.way_id
and wn1.sequence_id = wn2.sequence_id - 1
and n1.id = wn1.node_id
and n2.id = wn2.node_id
and wn1.way_id = w.id
and st_contains(g.object,n1.geom)
and st_contains(g.object,n2.geom)
order by wn1.way_id,wn1.sequence_id
) a

