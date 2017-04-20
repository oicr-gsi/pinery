SELECT p.partitionId, p.partitionNumber, r_spc.Run_runId, pool.alias AS pool_name, 
pool.identificationBarcode AS pool_barcode, pool.description AS pool_description 
FROM _Partition AS p 
JOIN Pool pool ON pool.poolId = p.pool_poolId 
JOIN SequencerPartitionContainer_Partition AS spc_p ON spc_p.partitions_partitionId = p.partitionId 
JOIN Run_SequencerPartitionContainer AS r_spc ON r_spc.containers_containerId = spc_p.container_containerId
