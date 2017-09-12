SELECT s.alias NAME 
        ,s.description description 
        ,s.NAME id 
        ,parent.NAME parentId 
        ,COALESCE(tt.sampleTypeName, sc.alias) sampleType
        ,sc.sampleCategory sample_category
        ,NULL sampleType_platform 
        ,NULL sampleType_description 
        ,tt.alias tissueType 
        ,p.shortName project 
        ,sai.archived archived 
        ,s.created created 
        ,s.creator createdById 
        ,s.lastModified modified 
        ,s.lastModifier modifiedById 
        ,s.identificationBarcode tubeBarcode 
        ,s.volume volume 
        ,sai.concentration concentration 
        ,s.locationBarcode storageLocation 
        ,NULL kitName 
        ,NULL kitDescription 
        ,NULL library_design_code 
        ,s.receivedDate receive_date 
        ,i.externalName external_name 
        ,i.donorSex sex
        ,tor.alias tissue_origin 
        ,tm.alias tissue_preparation 
        ,st.region tissue_region 
        ,st.secondaryIdentifier tube_id
        ,ss.strStatus str_result 
        ,sai.groupId group_id 
        ,sai.groupDescription group_id_description 
        ,sp.alias purpose 
        ,qubit.results qubit_concentration 
        ,nanodrop.results nanodrop_concentration 
        ,NULL barcode 
        ,NULL barcode_two 
        ,qpcr.results qpcr_percentage_human 
        ,s.qcPassed qcPassed 
        ,qpd.description detailedQcStatus 
        ,box.locationBarcode boxLocation 
        ,box.alias boxAlias 
        ,pos.position boxPosition 
        ,NULL paired 
        ,NULL read_length 
        ,NULL targeted_sequencing 
        ,'Sample' miso_type 
        ,sai.preMigrationId premigration_id 
        ,s.scientificName organism 
        ,subp.alias subproject
        ,it.alias institute
FROM Sample s 
LEFT JOIN DetailedSample sai ON sai.sampleId = s.sampleId 
LEFT JOIN DetailedQcStatus qpd ON qpd.detailedQcStatusId = sai.detailedQcStatusId 
LEFT JOIN Sample parent ON parent.sampleId = sai.parentId 
LEFT JOIN SampleClass sc ON sc.sampleClassId = sai.sampleClassId 
LEFT JOIN Project p ON p.projectId = s.project_projectId 
LEFT JOIN Subproject subp ON subp.subprojectId = sai.subprojectId 
LEFT JOIN Identity i ON i.sampleId = s.sampleId  
LEFT JOIN SampleAliquot sa ON sa.sampleId = sai.sampleId 
LEFT JOIN SamplePurpose sp ON sp.samplePurposeId = sa.samplePurposeId 
LEFT JOIN SampleTissue st ON st.sampleId = s.sampleId 
LEFT JOIN TissueType tt ON tt.tissueTypeId = st.tissueTypeId
LEFT JOIN TissueOrigin tor ON tor.tissueOriginId = st.tissueOriginId 
LEFT JOIN TissueMaterial tm ON tm.tissueMaterialId = st.tissueMaterialId
LEFT JOIN Lab la ON st.labId = la.labId
LEFT JOIN Institute it ON la.instituteId = it.instituteId
LEFT JOIN SampleStock ss ON sai.sampleId = ss.sampleId
LEFT JOIN (
	    SELECT sqc.sample_sampleId, MAX(sqc.qcId) AS qcId
	    FROM (
            SELECT sample_sampleId, qcMethod, MAX(qcDate) AS maxDate
	        FROM SampleQC
	        JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod
	        WHERE QCType.name = 'Qubit'
	        GROUP By sample_sampleId, qcMethod
	        ) maxQubitDates
	    JOIN SampleQC sqc ON sqc.sample_sampleId = maxQubitDates.sample_sampleId
	        AND sqc.qcDate = maxQubitDates.maxDate
	        AND sqc.qcMethod = maxQubitDates.qcMethod
	    GROUP BY sqc.sample_sampleId
		) newestQubit ON newestQubit.sample_sampleId = s.sampleId
LEFT JOIN SampleQC qubit ON qubit.qcId = newestQubit.qcId
LEFT JOIN (
        SELECT sqc.sample_sampleId, MAX(sqc.qcId) AS qcId
        FROM (
            SELECT sample_sampleId, qcMethod, MAX(qcDate) AS maxDate
            FROM SampleQC
            JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod
            WHERE QCType.name = 'Nanodrop'
            GROUP By sample_sampleId, qcMethod
            ) maxNanodropDates
        JOIN SampleQC sqc ON sqc.sample_sampleId = maxNanodropDates.sample_sampleId
            AND sqc.qcDate = maxNanodropDates.maxDate
            AND sqc.qcMethod = maxNanodropDates.qcMethod
        GROUP BY sqc.sample_sampleId
        ) newestNanodrop ON newestNanodrop.sample_sampleId = s.sampleId
LEFT JOIN SampleQC nanodrop ON nanodrop.qcId = newestNanodrop.qcId
LEFT JOIN (
        SELECT sqc.sample_sampleId, MAX(sqc.qcId) AS qcId
        FROM (
            SELECT sample_sampleId, qcMethod, MAX(qcDate) AS maxDate
            FROM SampleQC
            JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod
            WHERE QCType.name = 'Human qPCR'
            GROUP By sample_sampleId, qcMethod
            ) maxQpcrDates
        JOIN SampleQC sqc ON sqc.sample_sampleId = maxQpcrDates.sample_sampleId
            AND sqc.qcDate = maxQpcrDates.maxDate
            AND sqc.qcMethod = maxQpcrDates.qcMethod
        GROUP BY sqc.sample_sampleId
        ) newestQpcr ON newestQpcr.sample_sampleId = s.sampleId
LEFT JOIN SampleQC qpcr ON qpcr.qcId = newestQpcr.qcId
LEFT JOIN BoxPosition pos ON pos.targetId = s.sampleId 
        AND pos.targetType LIKE 'Sample%' 
LEFT JOIN Box box ON box.boxId = pos.boxId 
 
UNION ALL
 
SELECT l.alias NAME 
        ,l.description description 
        ,l.NAME id 
        ,parent.NAME parentId 
        ,NULL sampleType 
        ,NULL sample_category
        ,lt.platformType sampleType_platform 
        ,lt.description sampleType_description 
        ,NULL tissueType 
        ,LEFT(l.alias, LOCATE('_', l.alias)-1) project 
        ,lai.archived archived 
        ,l.creationDate created 
        ,l.creator createdById 
        ,l.lastModified modified 
        ,l.lastModifier modifiedById 
        ,l.identificationBarcode tubeBarcode 
        ,l.volume volume 
        ,l.concentration concentration 
        ,l.locationBarcode storageLocation 
        ,kd.NAME kitName 
        ,kd.description kitDescription 
        ,ldc.code library_design_code 
        ,NULL receive_date 
        ,NULL external_name 
        ,NULL sex
        ,NULL tissue_origin 
        ,NULL tissue_preparation 
        ,NULL tissue_region 
        ,NULL tube_id 
        ,NULL str_result 
        ,NULL group_id 
        ,NULL group_id_description 
        ,NULL purpose 
        ,qubit.results qubit_concentration 
        ,NULL nanodrop_concentration 
        ,bc1.sequence barcode 
        ,bc2.sequence barcode_two 
        ,NULL qpcr_percentage_human 
        ,l.qcPassed qcPassed 
        ,NULL detailedQcStatus 
        ,box.locationBarcode boxLocation 
        ,box.alias boxAlias 
        ,pos.position boxPosition 
        ,NULL paired 
        ,NULL readLength 
        ,NULL targeted_sequencing 
        ,'Library' miso_type 
        ,lai.preMigrationId premigration_id 
        ,NULL organism 
        ,NULL subproject
        ,NULL institute
FROM Library l 
LEFT JOIN Sample parent ON parent.sampleId = l.sample_sampleId
LEFT JOIN DetailedLibrary lai ON lai.libraryId = l.libraryId
LEFT JOIN KitDescriptor kd ON kd.kitDescriptorId = lai.kitDescriptorId
LEFT JOIN LibraryDesignCode ldc ON lai.libraryDesignCodeId = ldc.libraryDesignCodeId
LEFT JOIN LibraryType lt ON lt.libraryTypeId = l.libraryType
LEFT JOIN (
        SELECT lqc.library_libraryId, MAX(lqc.qcId) AS qcId
        FROM (
            SELECT library_libraryId, qcMethod, MAX(qcDate) AS maxDate
            FROM LibraryQC
            JOIN QCType ON QCType.qcTypeId = LibraryQC.qcMethod
            WHERE QCType.name = 'Qubit'
            GROUP By library_libraryId, qcMethod
            ) maxQubitDates
        JOIN LibraryQC lqc ON lqc.library_libraryId = maxQubitDates.library_libraryId
            AND lqc.qcDate = maxQubitDates.maxDate
            AND lqc.qcMethod = maxQubitDates.qcMethod
        GROUP BY lqc.library_libraryId
        ) newestQubit ON newestQubit.library_libraryId = l.libraryId
LEFT JOIN LibraryQC qubit ON qubit.qcId = newestQubit.qcId
LEFT JOIN ( 
        SELECT library_libraryId 
                ,sequence 
        FROM Library_Index 
        INNER JOIN Indices ON Indices.indexId = Library_Index.index_indexId 
        WHERE position = 1 
        ) bc1 ON bc1.library_libraryId = l.libraryId 
LEFT JOIN ( 
        SELECT library_libraryId 
                ,sequence 
        FROM Library_Index 
        INNER JOIN Indices ON Indices.indexId = Library_Index.index_indexId 
                WHERE position = 2 
        ) bc2 ON bc2.library_libraryId = l.libraryId 
LEFT JOIN BoxPosition pos ON pos.targetId = l.libraryId 
        AND pos.targetType LIKE 'Library%' 
LEFT JOIN Box box ON box.boxId = pos.boxId
 
UNION ALL
 
SELECT parent.alias name 
        ,NULL description 
        ,d.NAME id 
        ,parent.name parentId 
        ,NULL sampleType 
        ,NULL sample_category
        ,lt.platformType sampleType_platform 
        ,lt.description sampleType_description 
        ,NULL tissueType 
        ,LEFT(parent.alias, LOCATE('_', parent.alias)-1) project 
        ,0 archived 
        ,CONVERT(d.creationDate, DATETIME) created 
        ,NULL createdById 
        ,d.lastUpdated modified 
        ,d.lastModifier modifiedById 
        ,d.identificationBarcode tubeBarcode 
        ,d.volume volume 
        ,d.concentration concentration 
        ,NULL storageLocation 
        ,NULL kitName 
        ,NULL kitDescription 
        ,ldc.code library_design_code 
        ,NULL receive_date 
        ,NULL external_name 
        ,NULL sex
        ,NULL tissue_origin 
        ,NULL tissue_preparation 
        ,NULL tissue_region 
        ,NULL tube_id 
        ,NULL str_result 
        ,NULL group_id 
        ,NULL group_id_description 
        ,NULL purpose 
        ,NULL qubit_concentration 
        ,NULL nanodrop_concentration 
        ,NULL barcode 
        ,NULL barcode_two 
        ,NULL qpcr_percentage_human 
        ,1 qcPassed 
        ,NULL detailedQcStatus 
        ,NULL boxLocation 
        ,NULL boxAlias 
        ,NULL boxPosition 
        ,NULL paired 
        ,NULL readLength 
        ,NULL targeted_sequencing 
        ,'Dilution' miso_type 
        ,d.preMigrationId premigration_id 
        ,NULL organism 
        ,NULL subproject
        ,NULL institute
FROM LibraryDilution d 
JOIN Library parent ON parent.libraryId = d.library_libraryId 
JOIN LibraryType lt ON lt.libraryTypeId = parent.libraryType 
LEFT JOIN DetailedLibrary lai ON lai.libraryId = parent.libraryId 
LEFT JOIN LibraryDesignCode ldc ON lai.libraryDesignCodeId = ldc.libraryDesignCodeId
