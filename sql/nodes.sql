select distinct node, longitude, latitude
from(
select distinct source node,source_lng longitude,source_lat latitude
from
(
select wn1.way_id
,wn1.node_id source,st_x(n1.geom) source_lng,st_y(n1.geom) source_lat
,wn2.node_id target,st_x(n2.geom) target_lng,st_y(n2.geom) target_lat
,wn1.sequence_id,wn2.sequence_id ,
st_distance(ST_Transform(n1.geom,2163),ST_Transform(n2.geom,2163)) as cost,
w.tags ? 'oneway' as directed
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
union
select distinct target node,target_lng longitude,target_lat latitude
from
(
select wn1.way_id
,wn1.node_id source,st_x(n1.geom) source_lng,st_y(n1.geom) source_lat
,wn2.node_id target,st_x(n2.geom) target_lng,st_y(n2.geom) target_lat
,wn1.sequence_id,wn2.sequence_id ,
st_distance(ST_Transform(n1.geom,2163),ST_Transform(n2.geom,2163)) as cost,
w.tags ? 'oneway' as directed
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
) a
order by node