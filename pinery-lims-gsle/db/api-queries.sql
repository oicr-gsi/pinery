-- Name: /pinery/attributes
-- Description: LIMS API samples query
-- Application Properties: attributes

SELECT cdv.template_id
	,cdv.display_label
	,cdv.value
FROM custom_data_view cdv

-- Name: /pinery/barcodes
-- Description: lims api
-- Application Properties: barcode

SELECT ga.NAME
	,ga.bases
FROM ga_primer ga

-- TODO Not yet implemented into GsleClient
-- Name: /pinery/in.../{mid}/instrument/{id}
-- Description: LIMS API instrument query
-- Application Properties: 

SELECT instr_id
	,NAME
	,date_added
	,model_id
FROM instrument
WHERE model_id = ?
	AND instr_id = ?

-- Name: /pinery/inst.../{id}/instruments
-- Description: LIMS API instruments query
-- Application Properties: instrumentModelInstrumentList

SELECT instr_id
	,NAME
	,date_added
	,model_id
FROM instrument
WHERE model_id = ?

-- Name: /pinery/instrument/{id}
-- Description: LIMS API instrument query
-- Application Properties: instrumentSingle

SELECT instr_id
	,NAME
	,date_added
	,model_id
FROM instrument
WHERE instr_id = ?

-- Name: /pinery/instrumentmodel/{id}
-- Description: LIMS API instrumentmodel query
-- Application Properties: instrumentModelSingle

SELECT model_id
	,NAME
	,vendor
	,created_at
	,created_by
	,modified_at
	,modified_by
FROM ga_instrument_model
WHERE model_id = ?

-- Name: /pinery/instrumentmodels
-- Descrition: LIMS API instrumentmodels query
-- Application Properties: instrumentModelsList

SELECT model_id
	,NAME
	,vendor
	,created_at
	,created_by
	,modified_at
	,modified_by
FROM ga_instrument_model

-- Name: /pinery/instruments
-- Description: LIMS API instruments query
-- Application Properties: instrumentsList

 SELECT instr_id
	,NAME
	,date_added
	,model_id
FROM instrument

-- Name: /pinery/order/{id}
-- Description: LIMS API order query
-- Application Properties: orderSingle

SELECT DISTINCT order_id AS id
	,oo.created_by
	,oo.created_at
	,oo.modified_by
	,oo.modified_at
	,s.label AS STATUS
	,lg.lg_name AS project
	,obsc.label AS platform
FROM om_order oo
	,STATUS s
	,lab_group lg
	,om_order_form OF
	,om_custom_data ocd
	,om_order_form_field ooff
	,om_bfield_selection_choice obsc
WHERE oo.status_id = s.status_id
	AND oo.lab_group_id = lg.lab_group_id
	AND oo.order_form_id = OF.order_form_id
	AND ocd.form_field_id = ooff.form_field_id
	AND ocd.data = obsc.choice_id
	AND ooff.display_label LIKE 'Platform'
	AND ooff.order_form_id = oo.order_form_id
	AND order_id = ?

-- Name: /pinery/orders
-- Description: LIMS API orders query
-- Application Properties: ordersList

SELECT DISTINCT order_id AS id
	,oo.created_by
	,oo.created_at
	,oo.modified_by
	,oo.modified_at
	,s.label AS STATUS
	,lg.lg_name AS project
	,obsc.label AS platform
FROM om_order oo
	,STATUS s
	,lab_group lg
	,om_order_form OF
	,om_custom_data ocd
	,om_order_form_field ooff
	,om_bfield_selection_choice obsc
WHERE oo.status_id = s.status_id
	AND oo.lab_group_id = lg.lab_group_id
	AND oo.order_form_id = OF.order_form_id
	AND ocd.form_field_id = ooff.form_field_id
	AND ocd.data = obsc.choice_id
	AND ooff.display_label LIKE 'Platform'
	AND ooff.order_form_id = oo.order_form_id

-- Name: /pinery/ordersample/{id}
-- Description: LIMS API order sample query
-- Application Properties: temporaryOrderSingle

SELECT os.order_id
	,os.template_id AS sample_id
	,CASE 
		WHEN ga1.bases IS NULL
			THEN ga.bases
		ELSE ga1.bases
		END AS barcode
	,CASE 
		WHEN ga3.bases IS NULL
			THEN ga2.bases
		ELSE ga3.bases
		END AS barcode_two
	,ooff.display_label AS NAME
	,CASE obf.field_type
		WHEN 'Select One'
			THEN obsc.label
		ELSE ocd.data
		END AS value
FROM om_custom_data ocd
	,om_order_form_field ooff
	,om_bfield_selection_choice obsc
	,om_base_field obf
	,om_sample os
LEFT JOIN custom_data_view cdv ON (
		cdv.template_id = os.template_id
		AND cdv.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga ON (cdv.value LIKE ga.NAME)
LEFT JOIN ga_template_parents gtp ON (os.template_id = gtp.template_id)
LEFT JOIN custom_data_view cdv1 ON (
		cdv1.template_id = gtp.parent_id
		AND cdv1.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga1 ON (cdv1.value LIKE ga1.NAME)

LEFT JOIN custom_data_view cdv2 ON (
		cdv2.template_id = os.template_id
		AND cdv2.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga2 ON (cdv2.value LIKE ga2.NAME)
LEFT JOIN custom_data_view cdv3 ON (
		cdv3.template_id = gtp.parent_id
		AND cdv3.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga3 ON (cdv3.value LIKE ga3.NAME)
WHERE os.sample_id = ocd.object_id
	AND ocd.form_field_id = ooff.form_field_id
	AND ocd.data = obsc.choice_id
	AND ocd.base_field_id = obf.base_field_id
	AND os.template_id IS NOT NULL
	AND os.order_id = ?

-- Name: /pinery/ordersamples
-- Description: LIMS API order samples query
-- Application Properties: temporaryOrdersList

SELECT os.order_id
	,os.template_id AS sample_id
	,CASE 
		WHEN ga1.bases IS NULL
			THEN ga.bases
		ELSE ga1.bases
		END AS barcode
	,CASE 
		WHEN ga3.bases IS NULL
			THEN ga2.bases
		ELSE ga3.bases
		END AS barcode_two
	,ooff.display_label AS NAME
	,CASE obf.field_type
		WHEN 'Select One'
			THEN obsc.label
		ELSE ocd.data
		END AS value
FROM om_custom_data ocd
	,om_order_form_field ooff
	,om_bfield_selection_choice obsc
	,om_base_field obf
	,om_sample os
LEFT JOIN custom_data_view cdv ON (
		cdv.template_id = os.template_id
		AND cdv.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga ON (cdv.value LIKE ga.NAME)
LEFT JOIN ga_template_parents gtp ON (os.template_id = gtp.template_id)
LEFT JOIN custom_data_view cdv1 ON (
		cdv1.template_id = gtp.parent_id
		AND cdv1.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga1 ON (cdv1.value LIKE ga1.NAME)

LEFT JOIN custom_data_view cdv2 ON (
		cdv2.template_id = os.template_id
		AND cdv2.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga2 ON (cdv2.value LIKE ga2.NAME)
LEFT JOIN custom_data_view cdv3 ON (
		cdv3.template_id = gtp.parent_id
		AND cdv3.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga3 ON (cdv3.value LIKE ga3.NAME)
WHERE os.sample_id = ocd.object_id
	AND ocd.form_field_id = ooff.form_field_id
	AND ocd.data = obsc.choice_id
	AND ocd.base_field_id = obf.base_field_id
	AND os.template_id IS NOT NULL

-- Name: /pinery/position/{id}
-- Description: LIMS API position query
-- Application Properties: temporaryRunSingle

SELECT girt.run_id
	,girt.position
	,girt.template_id AS sample_id
	,CASE 
		WHEN ga1.bases IS NULL
			THEN ga.bases
		ELSE ga1.bases
		END AS barcode
	,CASE 
		WHEN ga3.bases IS NULL
			THEN ga2.bases
		ELSE ga3.bases
		END AS barcode_two
FROM ga_instrument_run_template girt
--
-- Match barcode in current template or in parent.
LEFT JOIN ga_template_parents gtp ON (girt.template_id = gtp.template_id)
LEFT JOIN custom_data_view cdv ON (
		cdv.template_id = gtp.parent_id
		AND cdv.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga ON (cdv.value LIKE ga.NAME)
LEFT JOIN custom_data_view cdv1 ON (
		cdv1.template_id = girt.template_id
		AND cdv1.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga1 ON (cdv1.value LIKE ga1.NAME)
--
-- Match second barcode in current template or in parent. 
LEFT JOIN custom_data_view cdv2 ON (
		cdv2.template_id = gtp.parent_id
		AND cdv2.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga2 ON (cdv2.value LIKE ga2.NAME)
LEFT JOIN custom_data_view cdv3 ON (
		cdv3.template_id = girt.template_id
		AND cdv3.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga3 ON (cdv3.value LIKE ga3.NAME)
WHERE girt.run_id = ?

-- Name: /pinery/positions
-- Description: LIMS API positions query
-- Application Properties: temporaryRunsList

SELECT girt.run_id
	,girt.position
	,girt.template_id AS sample_id
	,CASE 
		WHEN ga1.bases IS NULL
			THEN ga.bases
		ELSE ga1.bases
		END AS barcode
	,CASE 
		WHEN ga3.bases IS NULL
			THEN ga2.bases
		ELSE ga3.bases
		END AS barcode_two
FROM ga_instrument_run_template girt
--
-- Match barcode in current template or in parent.
LEFT JOIN ga_template_parents gtp ON (girt.template_id = gtp.template_id)
LEFT JOIN custom_data_view cdv ON (
		cdv.template_id = gtp.parent_id
		AND cdv.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga ON (cdv.value LIKE ga.NAME)
LEFT JOIN custom_data_view cdv1 ON (
		cdv1.template_id = girt.template_id
		AND cdv1.display_label LIKE 'Barcode'
		)
LEFT JOIN ga_primer ga1 ON (cdv1.value LIKE ga1.NAME)
--
-- Match second barcode in current template or in parent. 
LEFT JOIN custom_data_view cdv2 ON (
		cdv2.template_id = gtp.parent_id
		AND cdv2.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga2 ON (cdv2.value LIKE ga2.NAME)
LEFT JOIN custom_data_view cdv3 ON (
		cdv3.template_id = girt.template_id
		AND cdv3.display_label LIKE 'Barcode Two'
		)
LEFT JOIN ga_primer ga3 ON (cdv3.value LIKE ga3.NAME)

-- Name: /pinery/sample/changelogs
-- Description: LIMS API samples query
-- Application Properties: changeLogsList

SELECT gtcl.template_id
	,cl.cmnt
	,cl.notes
	,cl.created_by
	,cl.created_at
FROM ga_template gt
	,ga_template_change_log gtcl
	,change_log cl
WHERE gt.template_id = gtcl.template_id
	AND gtcl.change_log_id = cl.change_log_id


-- Name: /pinery/sample/{id}
-- Description: LIMS API samples query
-- Application Properties: sampleIdSingle

SELECT ga.template_id
	,ga.NAME
	,ga.description
	,ga.barcode AS tube_barcode
	,ga.volume
	,ga.concentration
	,ga.storage_location
	,ga.created_by
	,ga.created_at
	,ga.modified_by
	,ga.modified_at
	,ga.is_archived
	,gpk.label AS prep_kit_name
	,gpk.description AS prep_kit_description
	,gtt.label AS type_name
	,gtt.description AS type_description
	,s.label AS STATUS
	,gts.label AS STATE
FROM ga_template ga
LEFT JOIN ga_prep_kit gpk ON ga.prep_kit_id = gpk.prep_kit_id
INNER JOIN ga_template_type gtt ON ga.type_id = gtt.type_id
INNER JOIN STATUS s ON ga.status_id = s.status_id
INNER JOIN ga_template_state gts ON s.STATE = gts.template_state_id
WHERE ga.template_id = ?

-- TODO Not yet implemented in GsleClient
-- Name: /pinery/sample/{id}/changelog
-- Description: LIMS API samples query
-- Application Properties: 

SELECT gtcl.template_id
	,cl.cmnt
	,cl.notes
	,cl.created_by
	,cl.created_at
FROM ga_template gt
	,ga_template_change_log gtcl
	,change_log cl
WHERE gt.template_id = ?
	AND gt.template_id = gtcl.template_id
	AND gtcl.change_log_id = cl.change_log_id


-- Name: /pinery/samples
-- Description: LIMS API samples query
-- Application Properties: samples

SELECT ga.template_id
	,ga.NAME
	,ga.description
	,ga.barcode AS tube_barcode
	,ga.volume
	,ga.concentration
	,ga.storage_location
	,ga.created_by
	,ga.created_at
	,ga.modified_by
	,ga.modified_at
	,ga.is_archived
	,gpk.label AS prep_kit_name
	,gpk.description AS prep_kit_description
	,gtt.label AS type_name
	,gtt.description AS type_description
	,s.label AS STATUS
	,gts.label AS STATE
FROM ga_template ga
LEFT JOIN ga_prep_kit gpk ON ga.prep_kit_id = gpk.prep_kit_id
INNER JOIN ga_template_type gtt ON ga.type_id = gtt.type_id
INNER JOIN STATUS s ON ga.status_id = s.status_id
INNER JOIN ga_template_state gts ON s.STATE = gts.template_state_id
WHERE ga.is_archived IN (
		?
		,?
		)
	AND ga.NAME similar TO ?
	AND gtt.label similar TO ?
	AND ga.created_at > ?
	AND ga.modified_at < ?


-- Name: /pinery/samples/parents
-- Description: LIMS API samples query
-- Application Properties: parents

SELECT *
FROM ga_template_parents gtp

-- Name: /pinery/sequencerrun/{id}
-- Description: LIMS API sequencerrun query
-- Application Properties: runSingle

SELECT gir.run_id AS id
	,gir.NAME
	,gir.barcode
	,i.instr_id
	,i.NAME AS instrument_name
	,girs.label AS STATE
	,gir.created_by
	,gir.created_at
FROM ga_instrument_run gir
INNER JOIN ga_instrument_run_state girs ON (gir.STATE = girs.instrument_run_state_id)
LEFT JOIN instrument i ON (gir.instrument_id = i.instr_id)
WHERE gir.run_id = ?

-- Name: /pinery/sequencerruns
-- Description: LIMS API sequencerruns query
-- Application Properties: runsList

SELECT gir.run_id AS id
	,gir.NAME
	,gir.barcode
	,i.instr_id
	,i.NAME AS instrument_name
	,girs.label AS STATE
	,gir.created_by
	,gir.created_at
FROM ga_instrument_run gir
INNER JOIN ga_instrument_run_state girs ON (gir.STATE = girs.instrument_run_state_id)
LEFT JOIN instrument i ON (gir.instrument_id = i.instr_id)

-- Name: /pinery/user/{id}
-- Description: LIMS API samples query
-- Application Properties: userSingle

SELECT *
FROM finch_user fu
WHERE fu.user_id = ?


-- Name: /pinery/users
-- Description: LIMS API samples query
-- Application Properties: usersList

SELECT *
FROM finch_user
