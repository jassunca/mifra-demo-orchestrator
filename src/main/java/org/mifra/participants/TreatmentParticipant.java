package org.mifra.participants;

import org.mifra.core.api.models.domain.SagaStepMessage;
import org.mifra.core.api.models.domain.SagaStepOutcome;
import org.mifra.core.api.participant.Participant;
import org.mifra.core.components.domain.messages.SagaStepHistory;
import org.mifra.messages.saga.TreatmentStepPayload;

/**
 * The treatment participant is a conditional participant example, that is only invoked if the analysis participant
 * finds an excessive order from the client request. Despite that condition, its operation is exactly the same as all
 * the other participants, including receiving the current saga step history and returning its own saga step message.
 */
public class TreatmentParticipant implements Participant {

    /**
     * Like with all participants, a participant step method is required to receive a SagaStepHistory and return a
     * SagaStepMessage with a payload that implements SagaStepPayload. In this case, the received history includes the
     * Request message and the AnalysisStep message, and its specific TreatmentStepPayload is parametrized as the
     * return message payload.
     * @param history The current saga step history containing the Request message and the AnalysisStep message.
     * @return a saga step message with a parametrized TreatmentStepPayload payload.
     */
    public SagaStepMessage<TreatmentStepPayload> treat(SagaStepHistory history) {

        TreatmentStepPayload payload = new TreatmentStepPayload();

        payload.setPossibleOrder(100);

        SagaStepMessage<TreatmentStepPayload> message = new SagaStepMessage<>(payload);

        /*
        The definition of the step outcome in the return message is crucial so that the next step can be identified.
         */
        message.setResultState(SagaStepOutcome.of("Treated"));

        return message;
    }
}
