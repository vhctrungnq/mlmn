package vn.com.vhc.sts.cni.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class EricssonXMLConverter extends STS_BasicConverter {

    private List<DataMap> maps = new ArrayList<DataMap>();

    private String header = "";
    private int fileId = -1;

    private static Logger logger =
        Logger.getLogger(EricssonXMLConverter.class.getName());

    public EricssonXMLConverter() {
        loadDataMapping();
    }

    private void loadDataMapping() {
        maps.add(new DataMap("UTRANCELL",
                             "pmNoCsStreamDchDiscNormal,pmNoCsStreamDchDiscAbnorm,pmTotNoRrcConnectReq,pmTotNoRrcConnectReqSuccess,pmTotNoRrcConnectReqCs,pmTotNoRrcConnectReqCsSucc,pmTotNoRrcConnectReqPs,pmTotNoRrcConnectReqPsSucc,pmChSwitchFachIdle,pmSamplesCs12RabEstablish,pmSumCs12RabEstablish,pmNoLoadSharingRrcConn,pmNoPsStream64Ps8DchDiscNormal,pmNoPsStream64Ps8DchDiscAbnorm,pmSamplesBestCs12Establish,pmSumBestCs12Establish,pmNoDirRetryAtt,pmCellDowntimeAuto,pmCellDowntimeMan,pmTotalTimeUlCellCong,pmTotalTimeDlCellCong,pmNoOfNonHoReqDeniedSpeech,pmNoOfNonHoReqDeniedCs,pmNoOfNonHoReqDeniedInteractive,pmNoRabEstablishAttemptSpeech,pmNoRabEstablishAttemptCs64,pmNoRabEstablishAttemptCs57,pmNoRabEstablishAttemptPacketInteractive,pmNoRabEstablishAttemptPacketStream,pmNoRabEstablishSuccessSpeech,pmNoRabEstablishSuccessCs64,pmNoRabEstablishSuccessCs57,pmNoRabEstablishSuccessPacketInteractive,pmNoRabEstablishSuccessPacketStream,pmNoNormalRabReleasePacket,pmNoNormalRabReleaseSpeech,pmNoSystemRabReleasePacket,pmNoSystemRabReleaseSpeech,pmNoNormalRabReleasePacketStream,pmNoSystemRabReleasePacketStream,pmNoNormalRabReleaseCsStream,pmNoNormalRabReleaseCs64,pmNoSystemRabReleaseCsStream,pmNoSystemRabReleaseCs64,pmNoOfNonHoReqDeniedPsStreaming,pmNoRabEstablishAttemptPacketInteractiveHs,pmNoRabEstablishSuccessPacketInteractiveHs,pmNoNormalRbReleaseHs,pmNoSystemRbReleaseHs,pmNoIncomingHsHardHoAttempt,pmNoOutgoingHsHardHoAttempt,pmNoHsHardHoReturnOldChSource,pmNoHsHardHoReturnOldChTarget,pmNoOfNonHoReqDeniedHs,pmSamplesBestPsHsAdchRabEstablish,pmSumBestPsHsAdchRabEstablish,pmNoRabEstablishSuccessPacketStream128,pmNoRabEstablishAttemptPacketStream128,pmSamplesBestPsStr128Ps8RabEstablish,pmSumBestPsStr128Ps8RabEstablish,pmNoNormalRabReleasePacketStream128,pmNoSystemRabReleasePacketStream128,pmNoOfNonHoReqDeniedPsStr128,pmSamplesBestCs57RabEstablish,pmSumBestCs57RabEstablish,pmSamplesBestCs64RabEstablish,pmSumBestCs64RabEstablish,pmSamplesFachPsIntRabEstablish,pmSumFachPsIntRabEstablish,pmSamplesBestDchPsIntRabEstablish,pmSumBestDchPsIntRabEstablish,pmSamplesBestPsStr64Ps8RabEstablish,pmSumBestPsStr64Ps8RabEstablish,pmSamplesDchUlRlcTotPacketThp,pmSumDchUlRlcTotPacketThp,pmSamplesDchDlRlcTotPacketThp,pmSumDchDlRlcTotPacketThp,pmSamplesDchUlRlcUserPacketThp,pmSumDchUlRlcUserPacketThp,pmSamplesDchDlRlcUserPacketThp,pmSumDchDlRlcUserPacketThp,pmNoRrcCsReqDeniedAdm,pmSamplesPsInteractive,pmSumPsInteractive,pmNoRabEstablishAttemptPacketInteractiveEul,pmNoRabEstablishSuccessPacketInteractiveEul,pmNoSystemRbReleaseEul,pmNoNormalRbReleaseEul,pmSamplesBestPsEulRabEstablish,pmSumBestPsEulRabEstablish,pmRabEstablishEcAttempt,pmRabEstablishEcSuccess,pmNoOfNonHoReqDeniedEul,pmSumUlRlcUserThpPsStream16,pmSamplesUlRlcUserThpPsStream16,pmSumDlRlcUserThpPsStream64,pmSamplesDlRlcUserThpPsStream64,pmSumDlRlcUserThpPsStream128,pmSamplesDlRlcUserThpPsStream128,pmNoRabEstAttemptPsStreamHs,pmNoRabEstSuccessPsStreamHs,pmNoNormalRabReleasePsStreamHs,pmNoSystemRabReleasePsStreamHs,pmSamplesPsStreamHsRabEst,pmSumPsStreamHsRabEst,pmSamplesBestPsStreamHsRabEst,pmSumBestPsStreamHsRabEst,pmSumDlRlcUserThpPsStreamHs,pmSamplesDlRlcUserThpPsStreamHs,pmSumUlRlcUserThpPsStream32,pmSamplesUlRlcUserThpPsStream32,pmNoIncomingPsStreamHsHhoAttempt,pmNoIncomingPsStreamHsHhoSuccess,pmNoOutgoingPsStreamHsHhoAttempt,pmNoOutgoingPsStreamHsHhoSuccess,pmNoPsStreamHsHhoReturnOldSource,pmNoPsStreamHsHhoReturnOldTarget,pmSumUlRlcUserThpPsStream128,pmSamplesUlRlcUserThpPsStream128,pmNoLoadSharingRrcConnCs,pmNoLoadSharingRrcConnPs,pmNoRabEstAttemptPsIntNonHs,pmNoRabEstSuccessPsIntNonHs,pmSamplesUlRlcUserThpPsStreamEul,pmSumUlRlcUserThpPsStreamEul,pmSamplesUlRlcUserThpPsCnvUnkEul,pmSumUlRlcUserThpPsCnvUnkEul,pmSamplesUlRlcUserThpPsSpeechEul,pmSumUlRlcUserThpPsSpeechEul,pmSamplesDlRlcUserThpPsCnvUnkHs,pmSumDlRlcUserThpPsCnvUnkHs,pmSamplesDlRlcUserThpPsSpeechHs,pmSumDlRlcUserThpPsSpeechHs,pmSamplesBestPsStreamEulRabEst,pmSumBestPsStreamEulRabEst,pmSamplesPsStreamEulRabEst,pmSumPsStreamEulRabEst,pmNoSystemRabReleasePsStreamEul,pmNoNormalRabReleasePsStreamEul,pmNoRabEstSuccessPsStreamEul,pmNoRabEstAttemptPsStreamEul,pmSamplesBestPsCnvUnkEulRabEst,pmSumBestPsCnvUnkEulRabEst,pmSamplesBestPsSpeechEulRabEst,pmSumBestPsSpeechEulRabEst,pmSamplesPsCnvUnkEulRabEst,pmSumPsCnvUnkEulRabEst,pmSamplesPsSpeechEulRabEst,pmSumPsSpeechEulRabEst,pmNoSystemRabReleasePsCnvUnkEul,pmNoNormalRabReleasePsCnvUnkEul,pmNoSystemRabReleasePsSpeechEul,pmNoNormalRabReleasePsSpeechEul,pmNoRabEstSuccessPsCnvUnkEul,pmNoRabEstAttemptPsCnvUnkEul,pmNoRabEstSuccessPsSpeechEul,pmNoRabEstAttemptPsSpeechEul,pmNoRrcReqDeniedAdmDlPwr,pmNoRrcReqDeniedAdmDlChnlCode,pmNoRrcReqDeniedAdmDlHw,pmNoRrcReqDeniedAdmUlHw,pmSamplesActiveUesBestCell,pmSumActiveUesBestCell,pmSamplesActiveDriftUesBestCell,pmSumActiveDriftUesBestCell,pmNoSuccRbReconfPsIntDch,pmNoSuccRbReconfOrigPsIntDch,pmNoSuccRbReconfPsIntHs,pmNoSuccRbReconfOrigPsIntHs,pmNoSuccRbReconfPsIntEul,pmNoSuccRbReconfOrigPsIntEul,pmPsIntHsToFachAtt,pmPsIntHsToFachSucc,pmPsIntDchToFachAtt,pmPsIntDchToFachSucc,pmNoNormalNasSignReleaseCs,pmNoSystemNasSignReleaseCs,pmNoNormalNasSignReleasePs,pmNoSystemNasSignReleasePs,pmTotNoRrcConnectSetup,pmTotNoRrcConnectReqSubTr,pmSumPacketLatency,pmSamplesPacketLatency,pmSumPacketLatencyPsStreamHs,pmSamplesPacketLatencyPsStreamHs",
                             "R_E_UTRANCELL_3G"));
        maps.add(new DataMap("TRAF",
                             "pmCmAttDlHls,pmCmAttDlSf2,pmCmAttUlHls,pmCmAttUlSf2,pmCmStop,pmCmSuccDlHls,pmCmSuccDlSf2,pmCmSuccUlHls,pmCmSuccUlSf2,pmDlTotHeadSizeCompPsCnvRhc,pmDlTotHeadSizeUncompPsCnvRhc,pmDlTotSizeUncompPsCnvRhc,pmDlTrafficVolumeAmr4750,pmDlTrafficVolumeAmr5900,pmDlTrafficVolumeAmr7950,pmDlTrafficVolumeAmrNbMm,pmDlTrafficVolumeAmrWb,pmDlTrafficVolumeCs12,pmDlTrafficVolumeCs57,pmDlTrafficVolumeCs64,pmDlTrafficVolumePs128,pmDlTrafficVolumePs16,pmDlTrafficVolumePs384,pmDlTrafficVolumePs64,pmDlTrafficVolumePs8,pmDlTrafficVolumePsCnvUnkEul,pmDlTrafficVolumePsCommon,pmDlTrafficVolumePsIntHs,pmDlTrafficVolumePsSpeechEul,pmDlTrafficVolumePsStr128,pmDlTrafficVolumePsStr16,pmDlTrafficVolumePsStr64,pmDlTrafficVolumePsStrHs,pmDlTrafficVolumeSrb136,pmDlTrafficVolumeSrb34,pmEnableEulHhoAttempt,pmEnableEulHhoSuccess,pmEnableHsHhoAttempt,pmEnableHsHhoSuccess,pmEulHarqTransmTti2Failure,pmEulMacesPduTti10DelivPsRabs,pmEulMacesPduTti10DelivSrb,pmEulMacesPduTti10UndelivPsRabs,pmEulMacesPduTti10UndelivSrb,pmEulMacesPduTti2DelivPsRabs,pmEulMacesPduTti2DelivSrb,pmEulMacesPduTti2UndelivPsRabs,pmEulMacesPduTti2UndelivSrb,pmEulToDchAttempt,pmEulToDchSuccess,pmFaultyTransportBlocksBcUl,pmHsToDchAttempt,pmHsToDchSuccess,pmHsdschOverloadDetection,pmInactivityMultiPsInt,pmInterFreqMeasCmStart,pmInterFreqMeasCmStop,pmInterFreqMeasNoCmStart,pmInterFreqMeasNoCmStop,pmIratHoGsmMeasCmStart,pmIratHoGsmMeasNoCmStart,pmNoDlChCodeAllocAltCodeCm,pmNoDlChCodeAllocAttemptCm,pmNoEulCcAttempt,pmNoEulCcSuccess,pmNoEulHardHoReturnOldChSource,pmNoEulHardHoReturnOldChTarget,pmNoInCsIratHoAdmFail,pmNoInCsIratHoAtt,pmNoInCsIratHoSuccess,pmNoIncomingEulHardHoAttempt,pmNoIncomingEulHardHoSuccess,pmNoIncomingPsCnvSpEulHhoAttempt,pmNoIncomingPsCnvSpEulHhoSuccess,pmNoIncomingPsCnvUnEulHhoAttempt,pmNoIncomingPsCnvUnEulHhoSuccess,pmNoNonServingCellReqDeniedEul,pmNoNormalRabReleaseAmrNb,pmNoNormalRabReleaseAmrWb,pmNoNormalReleaseSrbOnly136,pmNoNormalReleaseSrbOnly34,pmNoOfIurSwDownNgCong,pmNoOfIurTermCsCong,pmNoOfIurTermHsCong,pmNoOfIurTermSpeechCong,pmNoOfSwDownEulCong,pmNoOfSwDownHsCong,pmNoOfSwDownNgCong,pmNoOfSwDownNgHo,pmNoOfTermCsCong,pmNoOfTermSpeechCong,pmNoOutgoingEulHardHoAttempt,pmNoOutgoingEulHardHoSuccess,pmNoOutgoingPsCnvSpEulHhoAttempt,pmNoOutgoingPsCnvSpEulHhoSuccess,pmNoOutgoingPsCnvUnEulHhoAttempt,pmNoOutgoingPsCnvUnEulHhoSuccess,pmNoPagingAttemptCnInitDcch,pmNoPagingAttemptUtranRejected,pmNoPagingType1Attempt,pmNoPagingType1AttemptCs,pmNoPagingType1AttemptPs,pmNoPsCnvSpEulHardHoReturnOldChSource,pmNoPsCnvSpEulHardHoReturnOldChTarget,pmNoPsCnvUnEulHardHoReturnOldChSource,pmNoPsCnvUnEulHardHoReturnOldChTarget,pmNoPsStream128Ps8DchDiscAbnorm,pmNoPsStream128Ps8DchDiscNormal,pmNoRabEstBlockRnBestPsStreamHs,pmNoRabEstBlockRnPsStreamHs,pmNoRabEstablishAttemptAmrNb,pmNoRabEstablishAttemptAmrWb,pmNoRabEstablishSuccessAmrNb,pmNoRabEstablishSuccessAmrWb,pmNoReceivedSabpMsgs,pmNoRejectedTcpConnections,pmNoRlDeniedAdm,pmNoRrcPsReqDeniedAdm,pmNoRrcReqDeniedAdm,pmNoSentSabpMsgs,pmNoServingCellReqDeniedEul,pmNoServingCellReqDeniedEulTti2,pmNoSystemRabReleaseAmrNb,pmNoSystemRabReleaseAmrWb,pmNoSystemReleaseSrbOnly136,pmNoSystemReleaseSrbOnly34,pmNoTimesIfhoCellFailAddToActSet,pmNoTimesIfhoRlAddToActSet,pmPsStreamHsToDchAttempt,pmPsStreamHsToDchSuccess,pmRlAddAttemptsBestCellCsConvers,pmRlAddAttemptsBestCellPacketHigh,pmRlAddAttemptsBestCellPacketLow,pmRlAddAttemptsBestCellPsConv,pmRlAddAttemptsBestCellSpeech,pmRlAddAttemptsBestCellStandAlone,pmRlAddAttemptsBestCellStream,pmRlAddSuccessBestCellCsConvers,pmRlAddSuccessBestCellPacketHigh,pmRlAddSuccessBestCellPacketLow,pmRlAddSuccessBestCellPsConv,pmRlAddSuccessBestCellSpeech,pmRlAddSuccessBestCellStandAlone,pmRlAddSuccessBestCellStream,pmSamplesAmr12200RabEstablish,pmSamplesAmr4750RabEstablish,pmSamplesAmr5900RabEstablish,pmSamplesAmr7950RabEstablish,pmSamplesAmrNbMmRabEstablish,pmSamplesAmrWbRabEstablish,pmSamplesBestAmr12200RabEstablish,pmSamplesBestAmr4750RabEstablish,pmSamplesBestAmr5900RabEstablish,pmSamplesBestAmr7950RabEstablish,pmSamplesBestAmrNbMmRabEstablish,pmSamplesBestAmrWbRabEstablish,pmSamplesBestRrcOnlyEstablish,pmSamplesBestSrbOnly34,pmSamplesCompMode,pmSamplesCs12Ps0RabEstablish,pmSamplesCs12Ps64RabEstablish,pmSamplesCs57RabEstablish,pmSamplesCs64RabEstablish,pmSamplesDlRlcUserThpCsConv,pmSamplesDlRlcUserThpCsStream,pmSamplesDlRlcUserThpSpeech,pmSamplesPsStr64Ps8RabEstablish,pmSamplesRrcOnlyEstablish,pmSamplesSrbOnly34,pmSamplesUesWith1Rls1RlInActSet,pmSamplesUesWith1Rls2RlInActSet,pmSamplesUesWith1Rls3RlInActSet,pmSamplesUesWith2Rls2RlInActSet,pmSamplesUesWith2Rls3RlInActSet,pmSamplesUesWith2Rls4RlInActSet,pmSamplesUesWith3Rls3RlInActSet,pmSamplesUesWith3Rls4RlInActSet,pmSamplesUesWith4Rls4RlInActSet,pmSamplesUlRlcUserThpCsConv,pmSamplesUlRlcUserThpCsStream,pmSamplesUlRlcUserThpSpeech,pmSumAmr12200RabEstablish,pmSumAmr4750RabEstablish,pmSumAmr5900RabEstablish,pmSumAmr7950RabEstablish,pmSumAmrNbMmRabEstablish,pmSumAmrWbRabEstablish,pmSumBestAmr12200RabEstablish,pmSumBestAmr4750RabEstablish,pmSumBestAmr5900RabEstablish,pmSumBestAmr7950RabEstablish,pmSumBestAmrNbMmRabEstablish,pmSumBestAmrWbRabEstablish,pmSumBestRrcOnlyEstablish,pmSumBestSrbOnly34,pmSumCompMode,pmSumCs12Ps0RabEstablish,pmSumCs12Ps64RabEstablish,pmSumCs57RabEstablish,pmSumCs64RabEstablish,pmSumDlRlcUserThpCsConv,pmSumDlRlcUserThpCsStream,pmSumDlRlcUserThpSpeech,pmSumPsStr64Ps8RabEstablish,pmSumRrcOnlyEstablish,pmSumSrbOnly34,pmSumUesWith1Rls1RlInActSet,pmSumUesWith1Rls2RlInActSet,pmSumUesWith1Rls3RlInActSet,pmSumUesWith2Rls2RlInActSet,pmSumUesWith2Rls3RlInActSet,pmSumUesWith2Rls4RlInActSet,pmSumUesWith3Rls3RlInActSet,pmSumUesWith3Rls4RlInActSet,pmSumUesWith4Rls4RlInActSet,pmSumUlRlcUserThpCsConv,pmSumUlRlcUserThpCsStream,pmSumUlRlcUserThpSpeech,pmTotNoRrcConnectAttIratCcOrder,pmTotNoRrcConnectAttIratCellResel,pmTotNoRrcConnectFailCongIratCcOrder,pmTotNoRrcConnectFailCongIratCellResel,pmTotNoRrcConnectReqSms,pmTotNoRrcConnectSuccessIratCcOrder,pmTotNoRrcConnectSuccessIratCellResel,pmTotNoTermRrcConnectReq,pmTotNoTermRrcConnectReqCs,pmTotNoTermRrcConnectReqCsSucc,pmTotNoTermRrcConnectReqPs,pmTotNoTermRrcConnectReqPsSucc,pmTotNoTermRrcConnectReqSucc,pmTotalTimeHsdschOverload,pmTransportBlocksBcUl,pmUlTotHeadSizeCompPsCnvRhc,pmUlTotHeadSizeUncompPsCnvRhc,pmUlTotSizeUncompPsCnvRhc,pmUlTrafficVolumeAmr4750,pmUlTrafficVolumeAmr5900,pmUlTrafficVolumeAmr7950,pmUlTrafficVolumeAmrNbMm,pmUlTrafficVolumeAmrWb,pmUlTrafficVolumeCs12,pmUlTrafficVolumeCs57,pmUlTrafficVolumeCs64,pmUlTrafficVolumePs128,pmUlTrafficVolumePs16,pmUlTrafficVolumePs384,pmUlTrafficVolumePs64,pmUlTrafficVolumePs8,pmUlTrafficVolumePsCnvUnkEul,pmUlTrafficVolumePsCommon,pmUlTrafficVolumePsIntEul,pmUlTrafficVolumePsSpeechEul,pmUlTrafficVolumePsStr128,pmUlTrafficVolumePsStr16,pmUlTrafficVolumePsStr32,pmUlTrafficVolumeSrb136,pmUlTrafficVolumeSrb34",
                             "R_E_TRAF_3G"));
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        //insert record and update status in C_RAW_FILES_MLMN table
        if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
            fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
        }

        try {
            int blockCount = 0;
            //
            DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder =
                docBuilderFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new EntityResolver() {
                    public InputSource resolveEntity(String publicId,
                                                     String systemId) throws SAXException,
                                                                             IOException {
                        return new InputSource(new StringReader(""));
                    }
                });
            //parse file
            Document doc = docBuilder.parse(file);

            Node parentNode = doc.getElementsByTagName("mdc").item(0);
            NodeList nodeList = parentNode.getChildNodes();
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);
                if (node.getNodeType() == 3) {
                    continue;
                }
                if (node != null &&
                    node.getNodeName().equalsIgnoreCase("md")) {
                    try {
                        blockCount +=
                                extractMeasData(direcPath, file.getName(),
                                                node);
                    } catch (Exception e) {
                        logger.warn("Extract data faiule in node at index: " +
                                       index);
                    }
                }
            }

            if (blockCount == 0) {
                throw new STS_ConvertException("Not found necessary data in the file");
            }

            this.updateRecordStatus();
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    private int extractMeasData(String direcPath, String fName,
                                Node measData) throws Exception {
        Node measInfo = getChildByNodeName(measData, "mi");
        if (measInfo == null) {
            return 0;
        }

        String timestamp = "";
        List<String> measTypes = new ArrayList<String>();
        List<Node> measValues = new ArrayList<Node>();

        NodeList nodeList = measInfo.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node n = nodeList.item(index);
            if (n.getNodeType() == 3) {
                continue;
            }
            // meastype
            if (n.getNodeName().equalsIgnoreCase("mt")) {
                measTypes.add(n.getTextContent());
            }
            //time
            if (n.getNodeName().equalsIgnoreCase("mts")) {
                timestamp = n.getTextContent();
                timestamp = timestamp.substring(0, timestamp.length() - 1);
            }
            // value
            if (n.getNodeName().equalsIgnoreCase("mv")) {
                measValues.add(n);
            }
        }
        // validate meastype
        String key = this.getKey(measTypes);
        if (key == null) {
            return 0;
        }
        if (measValues.size() == 0) {
            return 0;
        }

        String fileName = key + "_" + getFileName(fName);
        File outFile = new File(direcPath, fileName);
        if (outFile.exists()) {
            outFile.delete();
        }
        // make header
        header = "Time" + separator + "BscId" + separator + "CellId";
        for (String s : measTypes) {
            if (header.length() > 0) {
                header += separator;
            }
            header += s;
        }

        // make BscId
        String bscId = this.getBSCId(measData);
        // make line data
        for (Node n : measValues) {
            try {
                String line = this.getMeasValue(n);
                line = timestamp + separator + bscId + separator + line;
                writeLine(outFile, line);
            } catch (Exception e) {
            }
        }
        // insert a record into database
        this.genarateDBRecord(fileName, getTableName(key));

        return 1;
    }

    private String getKey(List<String> types) {
        String key = null;
        if (this.maps.size() > 0) {
            for (DataMap dm : maps) {
                if (this.validateMeasType(types, dm.getMeasTypeList())) {
                    key = dm.getKey();
                    break;
                }
            }
        }
        return key;
    }

    private String getTableName(String key) {
        String tableName = "";
        if (this.maps.size() > 0) {
            for (DataMap dm : maps) {
                if (dm.getKey().equalsIgnoreCase(key)) {
                    tableName = dm.getTableName();
                    break;
                }
            }
        }
        return tableName;
    }

    private String getBSCId(Node node) {
        String bscId = "";

        String nednContent = "";
        Node neidNode = getChildByNodeName(node, "neid");
        NodeList nodeList = neidNode.getChildNodes();
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeName().equalsIgnoreCase("neun")) {
                    bscId = nodeList.item(i).getTextContent();
                }
                if (nodeList.item(i).getNodeName().equalsIgnoreCase("nedn")) {
                    nednContent = nodeList.item(i).getTextContent();
                }
            }
        }
        if (bscId.equalsIgnoreCase("") && !nednContent.equalsIgnoreCase("")) {
            int index = nednContent.lastIndexOf("=");
            bscId = nednContent.substring(index + 1, nednContent.length());
        }

        return bscId;
    }

    private String getMeasValue(Node measValue) {
        String value = "";

        NodeList nodeList = measValue.getChildNodes();
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == 3) {
                continue;
            }

            if (node.getNodeName().equalsIgnoreCase("moid")) {
                if (value.length() > 0) {
                    value += separator;
                }
                value += this.getCellId(node.getTextContent());
            } else if (node.getNodeName().equalsIgnoreCase("r")) {
                if (value.length() > 0) {
                    value += separator;
                }
                value += node.getTextContent();
            }
        }
        return value;
    }

    private boolean validateMeasType(List<String> types, String measTypeList) {
        if (types == null || types.size() == 0 || measTypeList.length() == 0) {
            return false;
        }
        String measTypes[] = measTypeList.split(",");
        if (measTypes.length > 0) {
            boolean flag = false;
            for (String s : measTypes) {
                flag = false;
                for (String t : types) {
                    if (t.equalsIgnoreCase(s)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == false) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeFileHeader(File outFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        List<String> list = getFileHeaderInfo();
        for (String s : list) {
            writer.write(commentChar + s);
            writer.newLine();
        }
        if (header != null && header.length() > 0) {
            writer.write(commentChar + " ORIGINAL HEADER:");
            writer.newLine();
            writer.write(commentChar + header);
            writer.newLine();
            writer.write(commentChar +
                         "---------------------------------------------");
            writer.newLine();
            writer.write(commentChar + getSimpleText(header));
            writer.newLine();
        }
        writer.close();
    }

    private void writeLine(File outFile, String line) throws IOException {
        //write header
        if (!outFile.exists()) {
            writeFileHeader(outFile);
        }
        //write data
        if (line != null && line.length() > 0) {
            BufferedWriter writer =
                new BufferedWriter(new FileWriter(outFile, true));
            writer.append(line);
            writer.newLine();
            writer.flush();
            writer.close();
        }
    }

    private final String[] ORIGINAL =
    { "Active", "Attempt", "Connect", "Establish", "Idle", "Incoming",
      "Interactive", "Normal", "Outgoing", "Packet", "Release", "Samples",
      "Sharing", "Source", "Speech", "Stream", "Success", "Switch", "System",
      "Throughput", "Traffic", "Volume", "Return", "Rejected", "pm" };
    private final String[] REPLAMENT =
    { "Act", "Att", "Conn", "Estlsh", "Idl", "Incm", "Intact", "Norm", "Outg",
      "Pkt", "Rels", "Sampl", "Shar", "Src", "Spch", "Strm", "Succ", "Swt",
      "Sys", "Thput", "Traf", "Vol", "Rtrn", "Rej", "" };

    private String getSimpleText(String text) {
        String sh = "";
        String elms[] = text.split(separator);
        if (elms.length > 0) {
            for (String col : elms) {
                for (int i = 0; i < ORIGINAL.length; i++) {
                    if (col.indexOf(ORIGINAL[i]) >= 0) {
                        col = col.replace(ORIGINAL[i], REPLAMENT[i]);
                    }
                }
                if (sh.length() > 0) {
                    sh += separator;
                }
                sh += col;
            }
        }
        return sh;
    }

    private String getCellId(String moid) {
        return moid.substring(moid.lastIndexOf("=") + 1, moid.length());
    }

    private Node getChildByNodeName(Node node, String name) {
        NodeList nodeList = node.getChildNodes();
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node n = nodeList.item(index);
                if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
                    return n;
                }
            }
        }
        return null;
    }

    private void updateRecordStatus() throws SQLException {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            // update state
            String queryStr = "update C_RAW_FILES_MLMN set \n" +
                "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" +
                "where FILE_ID = " + fileId;
            st.execute(queryStr);
            st.close();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    private void genarateDBRecord(String fileName,
                                  String tableName) throws SQLException {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();

            String queryStr = "select PATTERN_ID, \n" +
                "DAY, HOUR, NODE_NAME from C_RAW_FILES_MLMN \n" +
                "where FILE_ID = " + fileId;
            String patternId = "";
            String day = "";
            String hour = "";
            String nodeName = "";
            //
            ResultSet rs = st.executeQuery(queryStr);
            while (rs.next()) {
                patternId = rs.getString("PATTERN_ID");
                day = STS_Util.getTime(rs.getDate("DAY"));
                hour = rs.getString("HOUR");
                nodeName = rs.getString("NODE_NAME");
            }
            if (patternId.length() > 0) {
                queryStr = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" +
                        "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" +
                        "CONVERT_FLAG, IMPORT_FLAG, \n" +
                        "NODE_NAME, RAW_TABLE) values(SEQ_CRF.NEXTVAL, " +
                        patternId + ", '" + fileName +
                        "', to_date('" + day + "', '" +
                        STS_Setting.DB_TIME_FORMAT + "'), " + hour +
                        ", 1, 0, '" + nodeName + "', '" + tableName + "')";

                st.execute(queryStr);
            }

            rs.close();
            st.close();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    private class DataMap {
        private String key = "";
        private String measTypeList = "";
        private String tableName = "";

        public DataMap(String key, String measTypeList, String tableName) {
            this.key = key;
            this.measTypeList = measTypeList;
            this.tableName = tableName;
        }

        public String getKey() {
            return key;
        }

        public String getMeasTypeList() {
            return measTypeList;
        }

        public String getTableName() {
            return tableName;
        }
    }
}
