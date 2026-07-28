package org.mifra;


import org.mifra.core.MifraEngine;
import org.mifra.messages.external.ApprovalReplyBody;
import org.mifra.messages.external.ApprovalRequestBody;
import org.mifra.orchestrators.RequestApprovalOrchestrator;
import org.mifra.participants.AnalysisParticipant;
import org.mifra.participants.ApprovalParticipant;
import org.mifra.participants.TreatmentParticipant;

/**
 * This is the demonstration of an environment setup for the Order handling demo service. It shows how to instantiate
 * and register the defined orchestrator and participants to the Mifra Engine, which handles the application
 * bootstrapping.
 * After creating a Mifra Engine instance, an orchestrator is registered by providing: the endpoint path the request
 * is expected at, the class that represents the deserialized JSON request body, the class that represents the
 * object to serialize as the JSON reply body, and the orchestrator itself, that is instantiated beforehand. In this
 * example, the application will listen at the endpoint 'localhost:8080/order/request' and process requests in which
 * its payload in JSON format correctly deserializes to the class defined as the RequestBody (here being
 * ApprovalRequestBody).
 * Each participant is registered via the Mifra Engine by providing: the Step Label that identifies it in a
 * SagaStepMap (either as the initial step or as one of the defined next steps), and a lambda expression pointing to the
 * participant method that processes the step. The step label must match one of the step labels defined in an
 * orchestrator's SagaStepMap, as it will never be called otherwise.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        MifraEngine engine = new MifraEngine();

        RequestApprovalOrchestrator orch = new RequestApprovalOrchestrator();

        engine.registerOrchestrator("/order/request", ApprovalRequestBody.class, ApprovalReplyBody.class, orch);

        AnalysisParticipant analysisParticipant = new AnalysisParticipant();
        engine.registerParticipantStep("OrderAnalysis", analysisParticipant::analyse);

        TreatmentParticipant treatmentParticipant = new TreatmentParticipant();
        engine.registerParticipantStep("OrderTreatment", treatmentParticipant::treat);

        ApprovalParticipant approvalParticipant = new ApprovalParticipant();
        engine.registerParticipantStep("OrderApproval", approvalParticipant::approve);

        engine.startAndJoin();

    }
}