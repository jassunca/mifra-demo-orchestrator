package org.mifra.messages.external;

import org.mifra.core.api.models.external.payloads.ExternalRequestBody;

/**
 * This is the class that the orchestrator receives as the external client request's body. Because it's created by
 * automatically deserializing the JSON body object from the body, its format and content must match what is received
 * as the JSON body. Also, for correct generic typing, it must implement the ExternalRequestBody interface. In this
 * case, the request body that arrives in the external client request has the format:
 * {
 *     noOfOrders: 1 (or any other number of orders received)
 * }
 */
public class ApprovalRequestBody implements ExternalRequestBody {

    private int noOfOrders;

    public ApprovalRequestBody(){}

    public int getNoOfOrders() {return noOfOrders;}

    public void setNoOfOrders(int noOfOrders) {this.noOfOrders = noOfOrders;}
}
