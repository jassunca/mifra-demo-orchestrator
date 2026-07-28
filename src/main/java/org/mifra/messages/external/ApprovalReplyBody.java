package org.mifra.messages.external;

import org.mifra.core.api.models.external.payloads.ExternalReplyBody;

/**
 * This is the example for the ExternalReplyBody implementation that the demo orchestrator uses. Implementing
 * ExternalReplyBody is mandatory, and an instance of this class is serialized as the reply's body sent to the client.
 * In this case, the reply body a client would receive after a request would be, for example:
 * {
 *     decision: "Approved",
 *     details: "The order of 50 units has been approved."
 * }
 */
public class ApprovalReplyBody implements ExternalReplyBody {

    private String decision;
    private String details;

    public ApprovalReplyBody() {}

    public String getDecision() {return decision;}

    public void setDecision(String decision) {this.decision = decision;}

    public String getDetails() {return details;}

    public void setDetails(String details) {this.details = details;}
}
