package org.mifra.participants;

import org.mifra.core.api.models.domain.SagaStepHistory;
import org.mifra.core.api.models.domain.SagaStepMessage;
import org.mifra.core.api.participant.Participant;
import org.mifra.messages.saga.ApprovalStepPayload;
import org.mifra.messages.saga.RequestPayload;
import org.mifra.messages.saga.TreatmentStepPayload;

/**
 * This is the final participant of the demo saga. It takes the saga step history that may contain a variable amount of
 * saga messages (it may or may not include an order treatment message) and builds the final saga message that is
 * appended to the history of messages.
 */
public class ApprovalParticipant implements Participant {

    /**
     * At the stage this participant step method is called, the saga step history may contain either two or three
     * different messages. That is possible since step methods always receive the SagaStepHistory wrapper, regardless
     * of its contents.
     * @param history The saga step history that at this point contains either just the Request message and the
     *                AnalysisStep message, or the two and a TreatmentStep message.
     * @return The ApprovalStep message that is then appended to the saga step history.
     */
    public SagaStepMessage<ApprovalStepPayload> approve(SagaStepHistory history) {

        /**
        The Request message is always guaranteed to exist in the history, so no conditional check is needed.
         */
        RequestPayload request = history.getStep(RequestPayload.class).getPayload();

        String decision;
        String details;

        /**
        Because the TreatmentStep message may or may not exist, the SagaStepHistory has() boolean method verifies if a
        given message exists without trying to retrieve it.
         */
        if (history.has(TreatmentStepPayload.class)) {
            TreatmentStepPayload treatment = history.getStep(TreatmentStepPayload.class).getPayload();
            decision = "Approved";
            details = String.format("The order of %d units is not possible, %d units were ordered instead.", request.getNoOfOrders(), treatment.getPossibleOrder());
        } else {
            decision = "Approved";
            details = String.format("The order of %d units has been approved.", request.getNoOfOrders());
        }

        ApprovalStepPayload payload = new ApprovalStepPayload();

        payload.setDecision(decision);
        payload.setDetails(details);

        SagaStepMessage<ApprovalStepPayload> message = new SagaStepMessage<>(payload);

        /*
        Because this is the final step of the saga, a step outcome isn't attributed to the message, since there is no
        next step to check.
         */

        return message;
    }
}
