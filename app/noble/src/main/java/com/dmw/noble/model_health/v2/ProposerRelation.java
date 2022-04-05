package com.dmw.noble.model_health.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProposerRelation {

@SerializedName("status")
@Expose
private String status;
@SerializedName("message")
@Expose
private String message;
@SerializedName("proposer_relation_to_insured")
@Expose
private List<ProposerRelationToInsured> proposerRelationToInsured = null;

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public List<ProposerRelationToInsured> getProposerRelationToInsured() {
return proposerRelationToInsured;
}

public void setProposerRelationToInsured(List<ProposerRelationToInsured> proposerRelationToInsured) {
this.proposerRelationToInsured = proposerRelationToInsured;
}

}