package vn.com.vhc.sts.cni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import vn.com.vhc.sts.cni.converter.EricssonXMLConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_TestUtil;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class STS_TestConvert {

    public STS_TestConvert() {
        //        final Context context;
        //        try {
        //            context = getInitialContext();
        //            DataSource ds = (DataSource)context.lookup("jdbc/pms2DS");
        //            if (ds != null) {
        //                //ConvertService normalCtl = new ConvertService(ds);
        //                //normalCtl.processTasks();
        //
        //                ImportService importCtl = new ImportService(ds);
        //                importCtl.processTasks();
        //            } else {
        //                System.out.println("Khong tao duoc DataSource");
        //            }
        //        } catch (Exception e) {
        //            System.out.println("Khong tim thay data source");
        //            e.printStackTrace();
        //        }

    }


    public static void main(String[] args) {
        STS_TestConvert tconvert = new STS_TestConvert();

        tconvert.testPattern();
        //tconvert.convertData();
        //tconvert.doConvert();

        //tconvert.Write_To_File(100, "abc", "chao ban");
    }

    static File file =
        new File("D:/RESEARCH/convert_file/WRITE_TO_FILE" + 666 + ".txt");
    static boolean append = true;

    public static void Write_To_File(int var, String str, String str1) {
        try {
            FileWriter fw = new FileWriter(file, append);
            //br=new BufferedWriter(new FileWriter(file));
            if (file.exists()) {
                fw.append("Debug String:: " + var + " , " + str + " , " +
                          str1 + ".\n");
                fw.flush();
            } else {
                //file.createNewFile();
                fw.append("Debug String:: " + var + " , " + str + " , " +
                          str1 + ".\n");
                fw.flush();
            }
        } catch (Exception ex) {
            System.out.println("Exception Occured:: ");
            ex.printStackTrace();
        }
    }

    private void abc() {
        String s =
            "20091125093000  RSG011E H01103J 0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       900     0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0       0,0,0   0,0,0   0,0,0   0,0,0";
        int count = 0;
        List<String> list = new ArrayList<String>();
        while (true) {
            count++;
            try {
                list.add(s);
                System.out.println("Count: " + count);
            } catch (Exception e) {
                System.out.println("Count: " + count);
                e.printStackTrace();
                break;
            }
        }
    }

    private final String[] ORIGINAL =
    { "Active", "Attempt", "Connect", "Establish", "Idle", "Incoming",
      "Interactive", "Normal", "Outgoing", "Packet", "Release", "Samples",
      "Sharing", "Source", "Speech", "Stream", "Success", "Switch", "System",
      "Throughput" };
    private final String[] REPLAMENT =
    { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg",
      "Pkt", "Rels", "Sampl", "Shar", "Src", "Spch", "Strm", "Succ", "Swt",
      "Sys", "Thput" };

    private String separator = ",";

    private void test() {
        String col =
            "pmSumBestCs12Establish/pmSamplesBestCs12Establish+pmSumBestAmr12200RabEstablish/pmSamplesBestAmr12200RabEstablish+pmSumBestAmr7950RabEstablish/pmSamplesBestAmr7950RabEstablish+pmSumAmr5900RabEstablish/pmSamplesBestAmr5900RabEstablish+pmSumAmr4750RabEstablish/pmSamplesBestAmr47500RabEstablish+pmSumAmrWbRabEstablish/pmSamplesAmrWbRabEstablish+pmSumBestAmrNbMmRabEstablish/pmSamplesBestAmrNbMmRabEstablish";
        for (int i = 0; i < ORIGINAL.length; i++) {
            if (col.indexOf(ORIGINAL[i]) >= 0) {
                col = col.replace(ORIGINAL[i], REPLAMENT[i]);
            }
        }
        col = col.replaceAll("pm", "");

        System.out.println(col);
    }

    private String getSimpleText(String bc) {
        return "";
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://192.168.20.8:8080");
        return new InitialContext(env);
    }

    private void doConvert() {
        STS_IConverter convert = new EricssonXMLConverter();
        String fileName =
            "A20091208.0630+0700-0645+0700_SubNetwork=ONRM_ROOT_MO,SubNetwork=RBDTM1E,MeContext=RBDTM1E_statsfile.xml";

        File file = new File("D:/RESEARCH/convert_file/" + fileName);
        String destinationPath = "D:/RESEARCH/convert_file/converted";
        if (!file.exists()) {
            System.out.println("File not found !");
            return;
        }

        Hashtable<Byte, String> param = new Hashtable<Byte, String>();
        param.put(STS_Setting.SEPARATOR_KEY, "\t");
        param.put(STS_Setting.COMMENT_CHAR_KEY, "#");
        param.put(STS_Setting.FILE_ID_KEY, "66666");
        param.put(STS_Setting.NODE_NAME_KEY, "none");

        try {
            convert.convertFile(file, destinationPath, param);
        } catch (STS_ConvertException e) {
            e.printStackTrace();
        }
        System.out.println("Convert is success !");
    }

    private void testPattern() {

        String p = "(LOAD_TRANS).(.*)-([0-9]{8})-([0-9]{6}).(.*)";
        String fileName =
            "LOAD_TRANS.AGG_TELLABS8660_CE_01.slot1-20160216-152849.txt";

        STS_TestUtil.checkPattern(p, fileName);
    }

    private void viewIndexColumn() {
        String header =
            "# GMT-time;suspect;Bssgp_BEDownUnitDataDisc;Bssgp_BEDownUnitDataRcv;Bssgp_BEDownUnitDataSent;Bssgp_NormDownUnitDataDisc;Bssgp_NormDownUnitDataRcv;Bssgp_NormDownUnitDataSent;Bssgp_PremDownUnitDataDisc;Bssgp_PremDownUnitDataRcv;Bssgp_PremDownUnitDataSent;Bssgp_RP1UpUnitDataDisc;Bssgp_RP1UpUnitDataRcv;Bssgp_RP1UpUnitDataSent;Bssgp_RP2UpUnitDataDisc;Bssgp_RP2UpUnitDataRcv;Bssgp_RP2UpUnitDataSent;Bssgp_RP3UpUnitDataDisc;Bssgp_RP3UpUnitDataRcv;Bssgp_RP3UpUnitDataSent;Bssgp_RP4UpUnitDataDisc;Bssgp_RP4UpUnitDataRcv;Bssgp_RP4UpUnitDataSent;Bssgp_RPNoneUpUnitDataDisc;Bssgp_RPNoneUpUnitDataRcv;Bssgp_RPNoneUpUnitDataSent;Bssgp_UpUnitDataDisc;Bssgp_UpUnitDataRcv;Bssgp_UpUnitDataSent;Cha_M_CdrAbnormalRelease;Cha_M_CdrManagementIntervention;Cha_M_CdrMaxChangeCond;Cha_M_CdrNormalRelease;Cha_M_CdrSgsnChange;Cha_NbrOfChaMmCtx_MAX;Cha_NbrOfChaMmCtx_MEAN;Cha_NbrOfChaPdpCtx_MAX;Cha_NbrOfChaPdpCtx_MEAN;Cha_NbrOfDrpeInFifoFile_MAX;Cha_NbrOfDrpeInFifoFile_MEAN;Cha_NbrOfDrpeSntToCgf;Cha_NbrOfMsgRcvFromCgf;Cha_NbrOfMsgSntToCgf;Cha_NbrOfRreRcvFromCgfInvalidMessageFormat;Cha_NbrOfRreRcvFromCgfMandatoryIeIncorrect;Cha_NbrOfRreRcvFromCgfMandatoryIeMissing;Cha_NbrOfRreRcvFromCgfNoResourcesAvailable;Cha_NbrOfRreRcvFromCgfOptionalIeIncorrect;Cha_NbrOfRreRcvFromCgfRequestAccepted;Cha_NbrOfRreRcvFromCgfServiceNotSupported;Cha_NbrOfRreRcvFromCgfSystemFailure;Cha_NbrOfRreRcvFromCgfVersionNotSupported;Cha_NormalS_CdrAbnormalRelease;Cha_NormalS_CdrManagementIntervention;Cha_NormalS_CdrMaxChangeCond;Cha_NormalS_CdrNormalRelease;Cha_NormalS_CdrSgsnChange;Cha_NormalS_CdrTimeLimit;Cha_NormalS_CdrVolumeLimit;Cha_NormalS_SMO_Cdr;Cha_PrepaidS_CdrAbnormalRelease;Cha_PrepaidS_CdrCamelInitCall;Cha_PrepaidS_CdrManagementIntervention;Cha_PrepaidS_CdrMaxChangeCond;Cha_PrepaidS_CdrNormalRelease;Cha_PrepaidS_CdrSgsnChange;Cha_PrepaidS_CdrTimeLimit;Cha_PrepaidS_CdrVolumeLimit;Cha_PrepaidS_SMO_Cdr;Cha_S_SMT_Cdr;CipheredKBytes;CipheredLostUnitData;CipheredUnitData;DecipheredKBytes;DecipheredLostUnitData;DecipheredUnitData;Gmm_attInterSgsnRelocationNew;Gmm_succInterSgsnRelocationNew;Gtpc_DownMsgSigDiscGwc;Gtpc_DownMsgSigRecGwc;Gtpc_KbytesDiscGwc;Gtpc_KbytesRcvGwc;Gtpc_KbytesSentGwc;Gtpc_RejGwcNoResources;Gtpc_RejGwcNotSupported;Gtpc_RejGwcOtherCauses;Gtpc_RejGwcProtocolError;Gtpc_UpMsgSigSentGwc;Gtpc_attDnsParseGwc;Gtpc_attIdentificationFromNewSgsn;Gtpc_attSgsnContextFromNewSgsn;Gtpc_succDnsParseGwc;Gtpc_succIdentificationFromNewSgsn;Gtpc_succSgsnContextFromNewSgsn;Gmm_NbrOfAttachedRoamingSubs_MAX;Gmm_NbrOfAttachedRoamingSubs_MEAN;Gmm_NbrOfHomeSub_MAX;Gmm_NbrOfHomeSub_MEAN;Gmm_NbrOfNewSubs;Gmm_NbrOfRoamingSubs_MAX;Gmm_NbrOfRoamingSubs_MEAN;Gmm_NbrOfSubs_MAX;Gmm_NbrOfSubs_MEAN;Gmm_NbrOfVisitingForeignSub_MAX;Gmm_NbrOfVisitingForeignSub_MEAN;Gmm_NbrOfVisitingNationalSub_MAX;Gmm_NbrOfVisitingNationalSub_MEAN;Llc_11DownBytesRcv;Llc_11DownFramesRcv;Llc_11DownPacketsDisc;Llc_11UpBytesSent;Llc_11UpFramesDisc;Llc_11UpFramesLost;Llc_11UpFramesSent;Llc_1DownBytesRcv;Llc_1DownFramesRcv;Llc_1DownPacketsDisc;Llc_1UpBytesSent;Llc_1UpFramesDisc;Llc_1UpFramesLost;Llc_1UpFramesSent;Llc_3DownBytesRcv;Llc_3DownFramesRcv;Llc_3DownPacketsDisc;Llc_3UpBytesSent;Llc_3UpFramesDisc;Llc_3UpFramesLost;Llc_3UpFramesSent;Llc_5DownBytesRcv;Llc_5DownFramesRcv;Llc_5DownPacketsDisc;Llc_5UpBytesSent;Llc_5UpFramesDisc;Llc_5UpFramesLost;Llc_5UpFramesSent;Llc_7DownBytesRcv;Llc_7DownFramesRcv;Llc_7DownPacketsDisc;Llc_7UpBytesSent;Llc_7UpFramesDisc;Llc_7UpFramesLost;Llc_7UpFramesSent;Llc_9DownBytesRcv;Llc_9DownFramesRcv;Llc_9DownPacketsDisc;Llc_9UpBytesSent;Llc_9UpFramesDisc;Llc_9UpFramesLost;Llc_9UpFramesSent;Llc_DownBytesRcv;Llc_DownFramesRcv;Llc_DownPacketsDisc;Llc_ErrFramesRcv;Llc_NbrOfRetrFrames;Llc_UpBytesSent;Llc_UpFramesDisc;Llc_UpFramesLost;Llc_UpFramesSent;Bssap_AlertAckSent;Bssap_AlertRejectSent;Bssap_AlertRequestRcv;Bssap_GprsDetachAckRcv;Bssap_GprsDetachIndicationSent;Bssap_ImsiDetachAckRcv;Bssap_ImsiDetachIndicationSent;Bssap_LocationUpdateAcceptRcv;Bssap_LocationUpdateRejectRcv;Bssap_LocationUpdateRequestSent;Bssap_MMInformationRequestRcv;Bssap_MSActivityIndicationSent;Bssap_MSInformationRequestRcv;Bssap_MSInformationResponseSent;Bssap_MSUnreachableSent;Bssap_MobileStatusRcv;Bssap_MobileStatusSent;Bssap_PagingRejectSent;Bssap_PagingRequestRcv;Bssap_ResetAckRcv;Bssap_ResetAckSent;Bssap_ResetIndicationRcv;Bssap_ResetIndicationSent;Bssap_TMSIReallocationCompleteSent;Gmm_AuthCiphFailMac;Gmm_AuthCiphFailRes;Gmm_AuthCiphFailSync;Gmm_AuthCksnMismatch;Gmm_FailVlrBssIWImsiUnknown;Gmm_FailVlrBssIWNoLa;Gmm_FailVlrBssIWNoPlmn;Gmm_FailVlrIdReqPtmsi;Gmm_SubAuthVectorReuse;Gmm_SuccPTMSIRealloc;Gmm_attAuthInSgsn;Gmm_attIdentityReq;Gmm_succAuthInSgsn;Gmm_succIdentityReq;Map_AttMsPresentPS;Map_EmptyResponsesForAuthSetsFromHlr;Map_FailVLRLocUpdtMSUnknown;Map_FailVLRLocUpdtRoamNotAllowed;Map_HlrLocationUpdate;Map_SubCancelLoc;Map_SuccMsPresentPS;Map_SuccVLRLocationUpdates;Map_VLRLocUpdtHlrReset;Map_VlrDelSubDataRcv;Map_VlrInsSubDataRcv;Map_VlrOtherFail;Map_VlrProtocolErr;Map_VlrSystemFail;Map_VlrTcapAbort;Map_VlrTcapTimeOut;Map_attReqAuthSetsSentToHlrBySgsn;Map_succReqAuthSetsHlr;Mm_AuthInfoRcvFromHLRAV3;Mm_AuthInfoRcvFromHLRAV5;Gmm_AttCombiAttach;Gmm_AttGprsAttachImsiAttached;Gmm_CellChangeInterval1;Gmm_CellChangeInterval2;Gmm_CellChangeInterval3;Gmm_CellChangePingPongInterval1;Gmm_CellChangePingPongInterval2;Gmm_CellChangePingPongInterval3;Gmm_DurationOfSuccAttach;Gmm_FailCombiAttachRadioStatus;Gmm_FailCombiAttachTimeout;Gmm_FailCombiInterSgsnRaUpdateRadioStatus;Gmm_FailCombiInterSgsnRaUpdateSuspendBss;Gmm_FailCombiInterSgsnRaUpdateTimeout;Gmm_FailCombiIntraSgsnRaUpdateRadioStatus;Gmm_FailCombiIntraSgsnRaUpdateSuspendBss;Gmm_FailCombiIntraSgsnRaUpdateTimeout;Gmm_FailGprsAttachImsiAttachedRadioStatus;Gmm_FailGprsAttachImsiAttachedTimeout;Gmm_FailGprsAttachRadioStatus;Gmm_FailGprsAttachTimeout;Gmm_FailInterSgsnRaUpdateRadioStatus;Gmm_FailInterSgsnRaUpdateSuspendBss;Gmm_FailInterSgsnRaUpdateTimeout;Gmm_FailIntraSgsnRaUpdatePeriodicRadioStatus;Gmm_FailIntraSgsnRaUpdatePeriodicSuspendBss;Gmm_FailIntraSgsnRaUpdatePeriodicTimeout;Gmm_FailIntraSgsnRaUpdateRadioStatus;Gmm_FailIntraSgsnRaUpdateSuspendBss;Gmm_FailIntraSgsnRaUpdateTimeout;Gmm_GprsAttachComplete;Gmm_GprsImplicitDetach;Gmm_GprsInactiveMSDetachSgsn;Gmm_GprsRejAttachAccRestrData;Gmm_GprsRejRauAccRestrData;Gmm_InterSgsnRaUpdateComplete;Gmm_IntraSgsnRaUpdateComplete;Gmm_MsAttachDuration1;Gmm_MsAttachDuration2;Gmm_MsAttachDuration3;Gmm_MsAttachDuration4;Gmm_MsAttachDuration5;Gmm_MsAttachDuration6;Gmm_NbrOfAttachedSub_MAX;Gmm_NbrOfAttachedSub_MEAN;Gmm_NbrOfCamelAttachedSub_MAX;Gmm_NbrOfCamelAttachedSub_MEAN;Gmm_NbrOfSubReady_MAX;Gmm_NbrOfSubReady_MEAN;Gmm_NbrOfSubStandby_MAX;Gmm_NbrOfSubStandby_MEAN;Gmm_RejCombiAttachAccRestrData;Gmm_RejCombiAttachNetworkFailure;Gmm_RejCombiAttachNotAllowed;Gmm_RejCombiAttachProtocolError;Gmm_RejCombiAttachSgsnInternal;Gmm_RejGprsAttachImsiAttachedAccRestrData;Gmm_RejGprsAttachImsiAttachedNetworkFailure;Gmm_RejGprsAttachImsiAttachedNotAllowed;Gmm_RejGprsAttachImsiAttachedProtocolError;Gmm_RejGprsAttachImsiAttachedSgsnInternal;Gmm_SuccCombiAttach;Gmm_SuccGprsAttachImsiAttached;Gmm_SumOfMsAttachDuration;Gmm_attCipheringModeControl;Gmm_attCombiDetachMs;Gmm_attCombiInterSgsnRaUpdate;Gmm_attCombiIntraSgsnRaUpdate;Gmm_attGprsAttach;Gmm_attGprsAttachImsi;Gmm_attGprsAttachRandomCollision;Gmm_attGprsDetachMs;Gmm_attGprsDetachSgsn;Gmm_attImsiDetachMs;Gmm_attInterSgsnRaUpdate;Gmm_attIntraSgsnRaUpdate;Gmm_attIntraSgsnRaUpdatePeriodic;Gmm_attPacketSwitchingPaging;Gmm_attRejGsmOnlySubscribed;Gmm_rejCombiInterSgsnRaUpdateNetworkFailure;Gmm_rejCombiInterSgsnRaUpdateNotAllowed;Gmm_rejCombiInterSgsnRaUpdateSgsnInternal;Gmm_rejCombiIntraSgsnRaUpdateNetworkFailure;Gmm_rejCombiIntraSgsnRaUpdateNotAllowed;Gmm_rejCombiIntraSgsnRaUpdateSgsnInternal;Gmm_rejGprsAttachNetworkFailure;Gmm_rejGprsAttachNotAllowed;Gmm_rejGprsAttachProtocolError;Gmm_rejGprsAttachSgsnInternal;Gmm_rejInterSgsnRaUpdateNetworkFailure;Gmm_rejInterSgsnRaUpdateNotAllowed;Gmm_rejInterSgsnRaUpdateSgsnInternal;Gmm_rejIntraSgsnRaUpdateNetworkFailure;Gmm_rejIntraSgsnRaUpdateNotAllowed;Gmm_rejIntraSgsnRaUpdatePeriodicNetworkFailure;Gmm_rejIntraSgsnRaUpdatePeriodicNotAllowed;Gmm_rejIntraSgsnRaUpdatePeriodicSgsnInternal;Gmm_rejIntraSgsnRaUpdateSgsnInternal;Gmm_succCipheringModeControl;Gmm_succCombiInterSgsnRaUpdate;Gmm_succCombiIntraSgsnRaUpdate;Gmm_succGprsAttach;Gmm_succGprsDetachSgsn;Gmm_succInterSgsnRaUpdate;Gmm_succIntraSgsnRaUpdate;Gmm_succIntraSgsnRaUpdatePeriodic;Gmm_unsuccPacketSwitchingPaging;Camel_failActPdpCtxCamelRatio;Sm_ActPDPCtxDroppDueToOverload;Sm_AttActPDPCtxRedirAPN;Sm_AttBEMSActPDPCtx;Sm_AttBEMSDeactPDPCtx;Sm_AttBENWDeactPDPCtx;Sm_AttBackMSActPDPCtx;Sm_AttBackMSDeactPDPCtx;Sm_AttBackNWDeactPDPCtx;Sm_AttConvMSActPDPCtx;Sm_AttConvMSDeactPDPCtx;Sm_AttConvNWDeactPDPCtx;Sm_AttInterTHP1MSActPDPCtx;Sm_AttInterTHP1MSDeactPDPCtx;Sm_AttInterTHP1NWDeactPDPCtx;Sm_AttInterTHP23MSActPDPCtx;Sm_AttInterTHP23MSDeactPDPCtx;Sm_AttInterTHP23NWDeactPDPCtx;Sm_AttNormMSActPDPCtx;Sm_AttNormMSDeactPDPCtx;Sm_AttNormNWDeactPDPCtx;Sm_AttPremMSActPDPCtx;Sm_AttPremMSDeactPDPCtx;Sm_AttPremNWDeactPDPCtx;Sm_AttStreamMSActPDPCtx;Sm_AttStreamMSDeactPDPCtx;Sm_AttStreamNWDeactPDPCtx;Sm_AttSubsMSActPDPCtx;Sm_NbrActivePDPCtx_MAX;Sm_NbrActivePDPCtx_MEAN;Sm_NbrBEActivePDPCtx_MAX;Sm_NbrBEActivePDPCtx_MEAN;Sm_NbrBackActivePDPCtx_MAX;Sm_NbrBackActivePDPCtx_MEAN;Sm_NbrConvActivePDPCtx_MAX;Sm_NbrConvActivePDPCtx_MEAN;Sm_NbrInterTHP1ActivePDPCtx_MAX;Sm_NbrInterTHP1ActivePDPCtx_MEAN;Sm_NbrInterTHP23ActivePDPCtx_MAX;Sm_NbrInterTHP23ActivePDPCtx_MEAN;Sm_NbrNormActivePDPCtx_MAX;Sm_NbrNormActivePDPCtx_MEAN;Sm_NbrOfActivePDPCtxForeignSub_MAX;Sm_NbrOfActivePDPCtxForeignSub_MEAN;Sm_NbrOfActivePDPCtxHomeSub_MAX;Sm_NbrOfActivePDPCtxHomeSub_MEAN;Sm_NbrOfActivePDPCtxNationalSub_MAX;Sm_NbrOfActivePDPCtxNationalSub_MEAN;Sm_NbrPremActivePDPCtx_MAX;Sm_NbrPremActivePDPCtx_MEAN;Sm_NbrStreamActivePDPCtx_MAX;Sm_NbrStreamActivePDPCtx_MEAN;Sm_NbrSubsWithActivePdpInSgsn_MAX;Sm_NbrSubsWithActivePdpInSgsn_MEAN;Sm_NormActPDPCtxQoSRef;Sm_NormActPDPCtxReneg;Sm_PremActPDPCtxQoSRef;Sm_PremActPDPCtxReneg;Sm_RejActPDPCtxAuthenticationFailed;Sm_RejActPDPCtxByGgsn;Sm_RejActPDPCtxGbFailure;Sm_RejActPDPCtxInvalidTransactionId;Sm_RejActPDPCtxMessageDecoding;Sm_RejActPDPCtxNetworkFailure;Sm_RejActPDPCtxNoResources;Sm_RejActPDPCtxNsapiAlreadyUsed;Sm_RejActPDPCtxOptionNotSubscribed;Sm_RejActPDPCtxOptionNotSupported;Sm_RejActPDPCtxOptionOutOfOrder;Sm_RejActPDPCtxProtocolError;Sm_RejActPDPCtxReactivationRequired;Sm_RejActPDPCtxRedirAPN;Sm_RejActPDPCtxUnknownApn;Sm_RejActPDPCtxUnknownPdp;Sm_RejActPDPCtxUnspecified;Sm_SuccBEMSActPDPCtx;Sm_SuccBackMSActPDPCtx;Sm_SuccConvMSActPDPCtx;Sm_SuccInterTHP1MSActPDPCtx;Sm_SuccInterTHP23MSActPDPCtx;Sm_SuccNormMSActPDPCtx;Sm_SuccPremMSActPDPCtx;Sm_SuccStreamMSActPDPCtx;Sm_attActPDPCtxMSPerSgsn;Sm_attActPDPCtxMSPerSgsnAPN1;Sm_attActPDPCtxMSPerSgsnAPN2;Sm_attActPDPCtxMSPerSgsnAPN3;Sm_attActPDPCtxMSPerSgsnAPN4;Sm_attActPDPCtxMSPerSgsnAPN5;Sm_attMSActPdpCtxDyn;Sm_attMSDeactPDPCtx;Sm_attMSDeactPDPCtxGbFailure;Sm_attMSDeactPDPCtxNoResources;Sm_attMSDeactPDPCtxQoSNotAccepted;Sm_attMSDeactPDPCtxRegular;Sm_attNwDeactPDPCtxGbFailure;Sm_attNwDeactPDPCtxNetworkFailure;Sm_attNwDeactPDPCtxReactivationRequired;Sm_attNwDeactPDPCtxRegular;Sm_attSgsnDeactPDPCtxOnDetachMs;Sm_attSgsnDeactPDPCtxOnReattachMs;Sm_succActPDPCtxMSPerSgsn;Sm_succActPDPCtxMSPerSgsnAPN1;Sm_succActPDPCtxMSPerSgsnAPN2;Sm_succActPDPCtxMSPerSgsnAPN3;Sm_succActPDPCtxMSPerSgsnAPN4;Sm_succActPDPCtxMSPerSgsnAPN5;Sm_succMSActPdpCtxDyn;Sm_succMSDeactPDPCtx;Nsc_Congestion;Nsc_DownDiscUnitData;Nsc_DownUnitDataRcv;Nsc_DownUnitDataSent;Nsc_FlowCtrlCellDisc;Nsc_FlowCtrlCellRecv;Nsc_FlowCtrlMSDisc;Nsc_FlowCtrlMSRecv;Nsc_UpDiscUnitData;Nsc_UpUnitDataRcv;Nsc_UpUnitDataSent;Camel_failActPdpCtxCamelResource;Gtpc_DownMsgSigDisc;Gtpc_DownMsgSigRec;Gtpc_DownMsgSigRec_V0;Gtpc_DownMsgSigRec_V1;Gtpc_GsnFailure;Gtpc_GsnRestart;Gtpc_KbytesDisc;Gtpc_KbytesRcv;Gtpc_KbytesRcv_V0;Gtpc_KbytesRcv_V1;Gtpc_KbytesSent;Gtpc_KbytesSent_V0;Gtpc_KbytesSent_V1;Gtpc_RejGgsnAuthenticationFailed;Gtpc_RejGgsnNoResources;Gtpc_RejGgsnNonExistent;Gtpc_RejGgsnNotSupported;Gtpc_RejGgsnProtocolError;Gtpc_RejGgsnSystemFailure;Gtpc_RejSgsnConnecSuspend;Gtpc_RejSgsnIMSIUnknown;Gtpc_RejSgsnMSDetached;Gtpc_RejSgsnMSNotResponding;Gtpc_RejSgsnMSRefuses;Gtpc_RejSgsnNoResources;Gtpc_RejSgsnNotSupported;Gtpc_RejSgsnPTMSIMismatch;Gtpc_RejSgsnProtocolError;Gtpc_RejSgsnRoamingRestriction;Gtpc_RejSgsnSystemFailure;Gtpc_UpMsgSigSent;Gtpc_UpMsgSigSent_V0;Gtpc_UpMsgSigSent_V1;Gtpc_attDnsParse;Gtpc_succDnsParse;Gtpu_BEDownKbytesSent;Gtpu_BEDownPDUSent;Gtpu_BEUpKbytesSent;Gtpu_BEUpPDUSent;Gtpu_BackDownKbytesSent;Gtpu_BackDownPduSent;Gtpu_BackInterPduSent;Gtpu_BackIntraPduSent;Gtpu_BackUpKbytesSent;Gtpu_BackUpPduSent;Gtpu_ConvDownKbytesSent;Gtpu_ConvDownPduSent;Gtpu_ConvInterPduSent;Gtpu_ConvIntraPduSent;Gtpu_ConvUpKbytesSent;Gtpu_ConvUpPduSent;Gtpu_DiscDataOctGnGGSN;Gtpu_DiscDataPktGnGGSN;Gtpu_DownPDULength1;Gtpu_DownPDULength2;Gtpu_DownPDULength3;Gtpu_DownPDULength4;Gtpu_DownPDULength5;Gtpu_DownPDULength6;Gtpu_DownPacketsDisc;Gtpu_DownPduDisc;Gtpu_ErrorIndicRcv;Gtpu_ErrorIndicSnt;Gtpu_InDataOctGnGGSN;Gtpu_InDataOctGnGGSN_V0;Gtpu_InDataOctGnGGSN_V1;Gtpu_InDataPktGnGGSN;Gtpu_InDataPktGnGGSN_V0;Gtpu_InDataPktGnGGSN_V1;Gtpu_InterTHP1DownKbytesSent;Gtpu_InterTHP1DownPduSent;Gtpu_InterTHP1InterPduSent;Gtpu_InterTHP1IntraPduSent;Gtpu_InterTHP1UpKbytesSent;Gtpu_InterTHP1UpPduSent;Gtpu_InterTHP23DownKbytesSent;Gtpu_InterTHP23DownPduSent;Gtpu_InterTHP23InterPduSent;Gtpu_InterTHP23IntraPduSent;Gtpu_InterTHP23UpKbytesSent;Gtpu_InterTHP23UpPduSent;Gtpu_NormDownKbytesSent;Gtpu_NormDownPDUSent;Gtpu_NormUpKbytesSent;Gtpu_NormUpPDUSent;Gtpu_OutDataOctGnGGSN;Gtpu_OutDataOctGnGGSN_V0;Gtpu_OutDataOctGnGGSN_V1;Gtpu_OutDataPktGnGGSN;Gtpu_OutDataPktGnGGSN_V0;Gtpu_OutDataPktGnGGSN_V1;Gtpu_PremDownKbytesSent;Gtpu_PremDownPDUSent;Gtpu_PremUpKbytesSent;Gtpu_PremUpPDUSent;Gtpu_StreamDownKbytesSent;Gtpu_StreamDownPduSent;Gtpu_StreamInterPduSent;Gtpu_StreamIntraPduSent;Gtpu_StreamUpKbytesSent;Gtpu_StreamUpPduSent;Gtpu_UpPDULength1;Gtpu_UpPDULength2;Gtpu_UpPDULength3;Gtpu_UpPDULength4;Gtpu_UpPDULength5;Gtpu_UpPDULength6;Gtpu_UpPacketsDisc;Gtpu_UpPduDisc;Sm_AttBEGgsnDeactPDPCtx;Sm_AttBackGgsnDeactPDPCtx;Sm_AttConvGgsnDeactPDPCtx;Sm_AttInterTHP1GgsnDeactPDPCtx;Sm_AttInterTHP23GgsnDeactPDPCtx;Sm_AttNormGgsnDeactPDPCtx;Sm_AttPremGgsnDeactPDPCtx;Sm_AttStreamGgsnDeactPDPCtx;Sm_BEPDPCtxDuration1;Sm_BEPDPCtxDuration2;Sm_BEPDPCtxDuration3;Sm_BEPDPCtxDuration4;Sm_BEPDPCtxDuration5;Sm_BEPDPCtxDuration6;Sm_BackPDPCtxDuration1;Sm_BackPDPCtxDuration2;Sm_BackPDPCtxDuration3;Sm_BackPDPCtxDuration4;Sm_BackPDPCtxDuration5;Sm_BackPDPCtxDuration6;Sm_ConvPDPCtxDuration1;Sm_ConvPDPCtxDuration2;Sm_ConvPDPCtxDuration3;Sm_ConvPDPCtxDuration4;Sm_ConvPDPCtxDuration5;Sm_ConvPDPCtxDuration6;Sm_InterTHP1PDPCtxDuration1;Sm_InterTHP1PDPCtxDuration2;Sm_InterTHP1PDPCtxDuration3;Sm_InterTHP1PDPCtxDuration4;Sm_InterTHP1PDPCtxDuration5;Sm_InterTHP1PDPCtxDuration6;Sm_InterTHP23PDPCtxDuration1;Sm_InterTHP23PDPCtxDuration2;Sm_InterTHP23PDPCtxDuration3;Sm_InterTHP23PDPCtxDuration4;Sm_InterTHP23PDPCtxDuration5;Sm_InterTHP23PDPCtxDuration6;Sm_NormPDPCtxDuration1;Sm_NormPDPCtxDuration2;Sm_NormPDPCtxDuration3;Sm_NormPDPCtxDuration4;Sm_NormPDPCtxDuration5;Sm_NormPDPCtxDuration6;Sm_PremPDPCtxDuration1;Sm_PremPDPCtxDuration2;Sm_PremPDPCtxDuration3;Sm_PremPDPCtxDuration4;Sm_PremPDPCtxDuration5;Sm_PremPDPCtxDuration6;Sm_StreamPDPCtxDuration1;Sm_StreamPDPCtxDuration2;Sm_StreamPDPCtxDuration3;Sm_StreamPDPCtxDuration4;Sm_StreamPDPCtxDuration5;Sm_StreamPDPCtxDuration6;Sm_attGgsnDeactPDPCtx;Sm_attSgsnDeactPDPCtx;Sm_attSgsnUpdPDPCtx;Sm_succGgsnDeactPDPCtx;Sm_succSgsnDeactPDPCtx;Sm_succSgsnUpdPDPCtx;Map_HlrResetRcv;Map_MoForwardSmSent;Map_MtForwardSmRcv;Map_ReadyForSmSent;Sms_AttMemoryAvailablePS;Sms_AttMoRedirection;Sms_MoEffectivSMMATransfersToHLR;Sms_MoEffectiveTransfersToSmsIwMsc;Sms_MoFailCallBarred;Sms_MoFailDeliveryReportsGeneratedInSgsn;Sms_MoFailDeliveryReportsRelayedToMs;Sms_MoFailNoMemoryAvailable;Sms_MoFailOdb;Sms_MoFailRpProtocolError;Sms_MoFailSMMADeliveryReportsGeneratedInSgsn;Sms_MoFailSMMADeliveryReportsRelayedToMs;Sms_MoFailSmsOverGprsNotAllowed;Sms_MoMsgSMMATransferRequestsFromMs;Sms_MoMsgTransferRequestsFromMs;Sms_MoSuccDeliveries;Sms_MoSuccSMMADeliveries;Sms_MtEffectiveTransfersToMs;Sms_MtFailDeliveryReportsGeneratedInSgsn;Sms_MtFailDeliveryReportsRelayedToSmsGmsc;Sms_MtFailGmmProcedureOngoing;Sms_MtFailMemoryCapacityExceeded;Sms_MtFailNoMemoryAvailable;Sms_MtFailNoPageResponse;Sms_MtFailPagingNotAllowed;Sms_MtFailRpProtocolError;Sms_MtFailSdmAccessFailed;Sms_MtFailSmsOverGprsNotAllowed;Sms_MtFailSubscriberBusy;Sms_MtFailTimerTc1nExpiry;Sms_MtFailTimerTr1nExpiry;Sms_MtFailUnknownSubscriber;Sms_MtMsgTransferRequestsFromSmsGmsc;Sms_MtSuccDeliveries;Sms_SuccMemoryAvailablePS;Sms_SuccMoRedirection;Sndcp_DownOctetsSent;Sndcp_DownPacketsDisc;Sndcp_DownPacketsRcv;Sndcp_UpOctetsRcv;Sndcp_UpPacketsDisc;Sndcp_UpPacketsSent;Camel_NbrOfPdpCtxCamel_MAX;Camel_NbrOfPdpCtxCamel_MEAN;Camel_NbrOfSmsCtxCamel_MAX;Camel_NbrOfSmsCtxCamel_MEAN;Camel_NbrOfTcapMsgCamelRcv;Camel_NbrOfTcapMsgCamelSnt;Camel_TimeBetweenCreditBack1;Camel_TimeBetweenCreditBack2;Camel_TimeBetweenCreditBack3;Camel_TimeBetweenCreditBack4;Camel_TimeBetweenCreditBack5;Camel_TimeBetweenCreditBack6;Camel_TimeBetweenCreditConv1;Camel_TimeBetweenCreditConv2;Camel_TimeBetweenCreditConv3;Camel_TimeBetweenCreditConv4;Camel_TimeBetweenCreditConv5;Camel_TimeBetweenCreditConv6;Camel_TimeBetweenCreditInterTHP11;Camel_TimeBetweenCreditInterTHP12;Camel_TimeBetweenCreditInterTHP13;Camel_TimeBetweenCreditInterTHP14;Camel_TimeBetweenCreditInterTHP15;Camel_TimeBetweenCreditInterTHP16;Camel_TimeBetweenCreditInterTHP231;Camel_TimeBetweenCreditInterTHP232;Camel_TimeBetweenCreditInterTHP233;Camel_TimeBetweenCreditInterTHP234;Camel_TimeBetweenCreditInterTHP235;Camel_TimeBetweenCreditInterTHP236;Camel_TimeBetweenCreditStream1;Camel_TimeBetweenCreditStream2;Camel_TimeBetweenCreditStream3;Camel_TimeBetweenCreditStream4;Camel_TimeBetweenCreditStream5;Camel_TimeBetweenCreditStream6;Camel_attActPdpCtxTdpEstAck;Camel_attActivityTest;Camel_attCamelDialogues;Camel_attPdpCtxNetDeact;Camel_attPdpCtxScpDeact;Camel_attSmsSubmissionDeact;Camel_attSmsSubmissionScpDeact;Camel_attSmsSubmissionTdpCollInfo;Camel_failActPdpCtxScpReject;Camel_failActPdpCtxScpUnacc;Camel_failDialoguesScf;Camel_failDialoguesSsf;Camel_failSmsSubmissionCamelResource;Camel_failSmsSubmissionScpReject;Camel_failSmsSubmissionScpUnacc;Camel_succActPdpCtxScpAccept;Camel_succActPdpCtxScpUnacc;Camel_succActivityTest;Camel_succSmsSubmissionScpAccept;Camel_succSmsSubmissionScpUnacc";
        String sep = ";";

        String col = "Gmm_NbrOfSubs_MAX";
        String[] temp = header.split(sep);
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].equalsIgnoreCase(col)) {
                System.out.println(temp[i] + " - Index: " + i);
                break;
            }
        }
    }

    private void moveFile() {
        File file = new File("");
        File dir = new File("D:/RESOURCE/test/abc/abc.txt");
        dir.exists();
        try {
            dir.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        boolean success = file.renameTo(new File(dir, file.getName()));
        if (!success) {
            System.out.println("File was not successfully moved");
        }
    }

    private void convertData() {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        String path =
            "D:/RESEARCH/SGHCM_2A-SGSN2_HCM-SGSN..Perm_jobs.0806.csv";
        String direc = "D:/RESEARCH/des";

        try {
            File file = new File(path);
            reader = new BufferedReader(new FileReader(file));

            File outFile = new File(direc, file.getName());
            writer = new BufferedWriter(new FileWriter(outFile));

            String line = "";
            while ((line = reader.readLine()) != null) {
                //list.add(line.toUpperCase());
                line = line.toLowerCase();

                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}

