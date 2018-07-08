$(".attachSubs2g").simpletip({
	content : 'MAX(peak_attach_gb_users)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".attachSubs3g").simpletip({
	content : 'MAX(peak_attach_IU_users)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".attachSubsSgsn").simpletip({
	content : 'MAX(peak_attach_IU_users) + MAX(peak_attach_gb_users)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".attachSubsUtilitySgsn").simpletip({
	content : 'Sum(peak_attach_gb_users + peak_attach_lU_users )<br/>' +
		'--------------------------------------------------------- * 100<br/>' +
		'(total attach capacity license)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".attachSuccRate2g").simpletip({
	content : 'sum(succ_gprs_attach + fail_gprs_attach_sim_not_prov +fail_gprs_attach_no_cell_in_la + fail_gprs_attach_ser_na_plmn +fail_gprs_attach_ser_nonser_na + gprs_attach_fail_plmn_na + gprs_attach_fail_la_na + gprs_attach_fail_roaming_na + fail_gprs_attach_illegal_ms + fail_gprs_attach_illegal_me + fail_gprs_attach_collisions + fail_gprs_attach_due_ms_err)<br/>' +
		'------------------------------------------------------------------------------------------------------------------------------------ * 100<br/>' +
		'sum(succ_gprs_attach + fail_gprs_attach_illegal_ms + fail_gprs_attach_illegal_me + fail_gprs_attach_sim_not_prov + fail_gprs_attach_ser_nonser_na + gprs_attach_fail_plmn_na + gprs_attach_fail_la_na + gprs_attach_fail_roaming_na + fail_gprs_attach_ser_na_plmn + fail_gprs_attach_no_cell_in_la + fail_gprs_attach_22 + fail_gprs_attach_due_ms_err + fail_gprs_attach_due_radio_err + fail_gprs_attach_due_sgsn_err + fail_gprs_attach_hlr_vlr_err + fail_gprs_attach_collisions)',
	fixed : false
});
$(".attachSuccRate3g").simpletip({
	content : 'sum (iu_succ_gprs_attach + iu_fail_gprs_attach_3 + iu_fail_gprs_attach_6 + iu_fail_gprs_attach_7 + iu_fail_gprs_attach_8 + iu_fail_gprs_attach_11 + iu_fail_gprs_attach_12 + iu_fail_gprs_attach_13 + iu_fail_gprs_attach_14 + iu_fail_gprs_attach_15 + iu_fail_gprs_attach_ms_err +iu_fail_gprs_attach_collisions)<br/>'+
		'------------------------------------------------------------------------------------------------------------------------------------------ * 100<br/>'+
		'sum (iu_succ_gprs_attach + iu_fail_gprs_attach_3 + iu_fail_gprs_attach_6 + iu_fail_gprs_attach_7 + iu_fail_gprs_attach_8 + iu_fail_gprs_attach_11 + iu_fail_gprs_attach_12 + iu_fail_gprs_attach_13 + iu_fail_gprs_attach_14 + iu_fail_gprs_attach_15 + iu_fail_gprs_attach_22 + iu_fail_gprs_attach_oth_cause + iu_fail_gprs_attach_ms_err + iu_fail_gprs_attach_radio_err + iu_fail_gprs_attach_sgsn_err + iu_fail_gprs_attach_hlr_vlr_er + iu_fail_gprs_attach_collisions )',
	fixed : false
});
$(".attachSuccRateSgsn").simpletip({
	content : '100* SUM[Tử số 3G Attach Succ Rate (%) + Tử số 2G Attach Succ Rate (%)]/SUM[Mẫu số 3G Attach Succ Rate (%) + Mẫu số 2G Attach Succ Rate (%)]',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".activePdp2g").simpletip({
	content : 'Peak_gb_pdp_cont',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".activePdp3g").simpletip({
	content : 'peak_iu_pdp_cont',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".activePdpSgsn").simpletip({
	content : 'Max( Peak_gb_pdp_cont + peak_iu_pdp_cont)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".activeUtilizationPdpSgsn").simpletip({
	content : 'sum( Peak_gb_pdp_cont + peak_iu_pdp_cont)<br/>' +
		'----------------------------------------------- * 100<br/>' +
		'(total PDP context capacity license)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".pdpAttachSucc2g").simpletip({
	content : 'sum ( succ_mo_pdp_context_act + fail_mo_pdp_act_wrong_password + fail_mo_pdp_act_mis_unk_apn + fail_mo_pdp_act_unk_addr_type + fail_mo_pdp_act_req_se_op_ns + fail_mo_pdp_act_serv_opt_ns + fail_mo_pdp_act_nsapi_used + fail_pdp_act_mac_transmission + fail_pdp_act_ms_protocol_error)<br/>' +
		'----------------------------------------------------------------------------------------------------- * 100<br/>' +
		'sum ( succ_mo_pdp_context_act + fail_mo_pdp_act_insuf_res + fail_mo_pdp_act_mis_unk_apn + fail_mo_pdp_act_unk_addr_type + fail_mo_pdp_act_wrong_password + fail_mo_pdp_act_act_re_ggsn + fail_mo_pdp_act_serv_opt_ns + fail_mo_pdp_act_req_se_op_ns + mo_pdp_act_fail_so_out_of_ord + fail_mo_pdp_act_nsapi_used + fail_mo_pdp_act_inv_pdp_actmsg + fail_mo_pdp_context_act + fail_pdp_act_mac_transmission + fail_pdp_act_contact_lost + fail_pdp_act_cs_call + fail_pdp_act_ms_protocol_error + fail_pdp_act_due_no_resp )',
	fixed : false
});
$(".pdpAttachSucc3g").simpletip({
	content : 'sum (iu_succ_mo_pdp_con_act + iu_fail_mo_pdp_act_29 +iu_fail_mo_pdp_act_33 + iu_fail_mo_pdp_act_27 + iu_fail_mo_pdp_act_28 + iu_fail_mo_pdp_act_32 + iu_fail_pdp_act_max_retrans + iu_fail_pdp_act_ms_prot_error)<br/>'+
		'------------------------------------------------------------------------------------------------------------------------------------------ * 100<br/>'+
		'sum (iu_succ_mo_pdp_con_act + iu_fail_mo_pdp_act_26 + iu_fail_mo_pdp_act_27 + iu_fail_mo_pdp_act_28 + iu_fail_mo_pdp_act_29 + iu_fail_mo_pdp_act_30 + iu_fail_mo_pdp_act_32 + iu_fail_mo_pdp_act_33 + iu_fail_mo_pdp_act_34 +iu_fail_mo_pdp_act_oth + iu_fail_pdp_act_ms_prot_error + iu_fail_pdp_act_due_no_resp + iu_fail_pdp_act_max_retrans + iu_fail_pdp_act_contact_lost )',
	fixed : false
});
$(".pdpAttachSuccSgsn").simpletip({
	content : '[SUM(tử số 2G PDP Act Succ (%) + tử số 3G PDP Act Succ (%))/SUM(mẫu số 2G PDP Act Succ (%) + mẫu số 3G PDP Act Succ (%)]*100',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".userTraffic2g").simpletip({
	content : '( sum (gtp_data_in_bytes_sent_in_dl) + 4294967296 * gtp_data_bytes_sent_in_dl_ofl) – sum(iu_gtp_bytes_sent_in_dl + 4294967296 * iu_gtp_bytes_sent_in_dl_ofl))+(sum (gtp_data_in_bytes_sent_in_ul) + 4294967296 * gtp_data_bytes_sent_in_ul_ofl) - sum( iu_gtp_bytes_sent_in_ul + 4294967296 * iu_gtp_bytes_sent_in_ul_ofl))',
	fixed : false
});
$(".userTraffic3g").simpletip({
	content : 'sum (iu_gtp_bytes_sent_in_dl + 4294967296 * iu_gtp_bytes_sent_in_dl_ofl)+sum (iu_gtp_bytes_sent_in_ul + 4294967296 * iu_gtp_bytes_sent_in_ul_ofl)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".userTrafficSgsn").simpletip({
	content : '2G User traffic + 3G User traffic',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".dlThroughput2g").simpletip({
	content : '8 * ( sum (gtp_data_in_bytes_sent_in_dl) + 4294967296 * gtp_data_bytes_sent_in_dl_ofl) – sum(iu_gtp_bytes_sent_in_dl + 4294967296 * iu_gtp_bytes_sent_in_dl_ofl))<br>'+
		'-----------------------------------------------------------------------------------------------------------------------------------<br/>'+
		'sum (period duration) *60 * 1024 * 1024',
	fixed : false
});
$(".dlThroughput3g").simpletip({
	content : '8 * sum (iu_gtp_bytes_sent_in_dl + 4294967296 * iu_gtp_bytes_sent_in_dl_ofl)<br/>' +
	'--------------------------------------------------------------------------------------<br/>' +
	'sum( period_duration) * 60 * 1024 * 1024',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".dlThroughputSgsn").simpletip({
	content : '2G Throughput downlink + 3G Throughput downlink',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".ulThroughput2g").simpletip({
	content : '8 * (sum (gtp_data_in_bytes_sent_in_ul) + 4294967296 * gtp_data_bytes_sent_in_ul_ofl) - sum( iu_gtp_bytes_sent_in_ul + 4294967296 * iu_gtp_bytes_sent_in_ul_ofl))<br/>'+
		'-----------------------------------------------------------------------------------------------------------------------------------<br/>'+
		'sum (period duration) *60 * 1024 * 1024',
	fixed : false
});
$(".ulThroughput3g").simpletip({
	content : '8 * sum (iu_gtp_bytes_sent_in_ul + 4294967296 * iu_gtp_bytes_sent_in_ul_ofl)' +
		'------------------------------------------------------------------------------------- <br/>' +
		'sum( period_duration) * 60 * 1024 * 1024',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".ulThroughputSgsn").simpletip({
	content : '2G Throughput uplink + 3G Throughput uplink',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".throughputUtilizationSgsn").simpletip({
	content : '[2G +3G]Uplink throughput + [2G+ 3G]Downlink throughput' +
		'----------------------------------------------- * 100<br/>' + 
		'(total throughput capacity license)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".intraSgsnRauSucc2g").simpletip({
	content : 'Tử (2G Intra PAPU RAU Succ (%) +2G Inter PAPU RAU Succ (%))/mẫu(2G Intra PAPU RAU Succ (%) +2G Inter PAPU RAU Succ (%))*100',
	fixed : false
});
$(".intraSgsnRauSucc3g").simpletip({
	content : 'Tử (3G Intra PAPU RAU Succ (%) +3G Inter PAPU RAU Succ (%))/mẫu(3G Intra PAPU RAU Succ (%) +3G Inter PAPU RAU Succ (%))*100',
	fixed : false
});
$(".intraSgsnRauSuccSgsn").simpletip({
	content : 'Tử (3G Intra SGSN RAU Succ (%) + 2G Intra SGSN RAU Succ (%))/mẫu (3G Intra SGSN RAU Succ (%) + 2G Intra SGSN RAU Succ (%))*100',
	fixed : false
});
$(".interSgsnRauSucc2g").simpletip({
	content : 'sum(succ_inter_sgsn_ra_updat + inter_sgsn_rau_f_ill_ms + inter_sgsn_rau_f_ill_me + inter_sgsn_rau_f_gprs_na + inter_sgsn_rau_f_ms_ident + inter_sgsn_rau_f_plmn_na + inter_sgsn_rau_f_la_na + inter_sgsn_rau_f_roaming_na + inter_sgsn_rau_f_gprs_na_pl + inter_sgsn_rau_f_no_s_cell + inter_sgsn_rau_f_ms_ident + fail_inter_sgsn_rau_ms +fail_inter_sgsn_rau_coll)<br>'+
		'-------------------------------------------------------------------------------------------------------------------------------- * 100<br/>'+
		'sum(succ_inter_sgsn_ra_updat + inter_sgsn_rau_f_ill_ms + inter_sgsn_rau_f_ill_me + inter_sgsn_rau_f_gprs_na + inter_sgsn_rau_f_ms_ident + inter_sgsn_rau_f_imp_detach + inter_sgsn_rau_f_plmn_na + inter_sgsn_rau_f_la_na + inter_sgsn_rau_f_roaming_na + inter_sgsn_rau_f_gprs_na_pl + inter_sgsn_rau_f_no_s_cell + fail_inter_sgsn_rau_22 + fail_inter_sgsn_ra_updat + fail_inter_sgsn_rau_ms + fail_inter_sgsn_rau_radio + fail_inter_sgsn_rau_coll + fail_inter_sgsn_rau_sgsn + fail_inter_sgsn_rau_hlrvlr)',
	fixed : false
});
$(".interSgsnRauSucc3g").simpletip({
	content : 'sum (iu_succ_in_inter_sgsn_ra_upd + iu_inter_sgsn_rau_f_3 + iu_inter_sgsn_rau_f_6 + iu_inter_sgsn_rau_f_7 + iu_inter_sgsn_rau_f_9 +iu_inter_sgsn_rau_f_11 + iu_inter_sgsn_rau_f_12 + iu_inter_sgsn_rau_f_13 + iu_inter_sgsn_rau_f_14 + iu_inter_sgsn_rau_f_15 + iu_fail_inter_sgsn_rau_ms + iu_fail_inter_sgsn_rau_coll)<br/>'+
		'------------------------------------------------------------------------------------------------------------------------------------------- *100<br/>'+
		'sum(iu_succ_in_inter_sgsn_ra_upd + iu_inter_sgsn_rau_f_3 + iu_inter_sgsn_rau_f_6 + iu_inter_sgsn_rau_f_7 + iu_inter_sgsn_rau_f_9 + iu_inter_sgsn_rau_f_10 + iu_inter_sgsn_rau_f_11 + iu_inter_sgsn_rau_f_12 + iu_inter_sgsn_rau_f_13 + iu_inter_sgsn_rau_f_14 + iu_inter_sgsn_rau_f_15 + iu_fail_inter_sgns_rau_22 + iu_inter_sgsn_rau_f_oth_cause + iu_fail_inter_sgsn_rau_ms + iu_fail_inter_sgsn_rau_radio + iu_fail_inter_sgsn_rau_coll + iu_fail_inter_sgsn_rau_sgsn + iu_fail_inter_sgsn_rau_hlrvlr)',
	fixed : false
});
$(".interSgsnRauSuccSgsn").simpletip({
	content : 'Tử (2G Inter SGSN RAU Succ (%) + 3G Inter SGSN RAU Succ (%))/mẫu ((2G Inter SGSN RAU Succ (%) + 3G Inter SGSN RAU Succ (%))*100',
	fixed : false
});
$(".rate2g3gIntraHoSgsn").simpletip({
	content : 'Tử (2G to 3G intra PAPU HO (%)+ 2G to 3G inter PAPU HO (%))/(mẫu (2G to 3G intra PAPU HO (%)+ 2G to 3G inter PAPU HO (%))*100)',
	fixed : false
});
$(".rate3g2gIntraHoSgsn").simpletip({
	content : 'Tử (3G to 2G intra PAPU HO (%)+ 3G to 2G inter PAPU HO (%))/(mẫu (3G to 2G intra PAPU HO (%)+ 3G to 2G inter PAPU HO (%))*100)',
	fixed : false
});
$(".rate2g3gInterHoSgsn").simpletip({
	content : 'Sum(succ_outg_inter_sys_rau)<br/>' +
		'------------------------------------------------------- * 100<br/>'+
		'Sum(succ_outg_inter_sys_rau + fail_outg_inter_sys_rau)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".rate3g2gInterHoSgsn").simpletip({
	content : 'Sum(iu_succ_og_inte_sgsn_3g2g_isho)<br/>'+
		'---------------------------------------------------------------------- * 100<br/>'+
		'Sum(iu_succ_og_inte_sgsn_3g2g_isho + iu_fail_og_inte_sgsn_3g2g_isho)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".authenSucc2g").simpletip({
	content : 'Sum(GSM_succ_mm_auth + GSM_succ_sm_auth)<br/>'+
		'----------------------------------------------------- * 100<br/>'+
		'Sum(gsm_auth_attempts)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".authenSucc3g").simpletip({
	content : 'Sum(UMTS_succ_mm_auth + UMTS_succ_sm_auth)<br/>'+
		'--------------------------------------------------------- * 100<br/>'+
		'Sum(UMTS_auth_attempts)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".authenSuccSgsn").simpletip({
	content : 'Tử (3G Authen Succ (%) + 2G Authen Succ (%))/mẫu(3G Authen Succ (%) + 2G Authen Succ (%))*100',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".pagingSucc3g").simpletip({
	content : 'Sum(sgsn_level_iu_ps_pagings – sgsn_level_unsucc_ps_pag)<br/>'+
		'-----------------------------------------------------------------------* 100<br/>'+
		'Sum(sgsn_level_iu_ps_pagings)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".pagingSucc2g").simpletip({
	content : 'Sum(sgsn_level_ps_pagings – sgsn_level_unsucc_ps_pag)<br/>'+
		'------------------------------------------------------------- * 100<br/>'+
		'Sum(sgsn_level_ps_pagings)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".pagingSuccSgsn").simpletip({
	content : 'Tử (2G Paging Succ (%) + 3G Paging Succ (%))/mẫu(2G Paging Succ (%) + 3G Paging Succ (%))*100',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".peakCpu").simpletip({
	content : 'Max(peak_load_rate_of_object)',
	fixed : false,
	baseClass : 'tooltip2'
});
$(".averageCpu").simpletip({
	content : 'sum(ave_load_rate_sum)/sum(ave_load_rate_den)',
	fixed : false,
	baseClass : 'tooltip2'
});