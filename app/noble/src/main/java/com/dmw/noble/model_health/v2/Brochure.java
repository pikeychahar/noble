package com.dmw.noble.model_health.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brochure {

@SerializedName("policy_wording")
@Expose
private String policyWording;
@SerializedName("proposal_form")
@Expose
private String proposalForm;
@SerializedName("claim_form")
@Expose
private String claimForm;
@SerializedName("brochure")
@Expose
private String brochure;

public String getPolicyWording() {
return policyWording;
}

public void setPolicyWording(String policyWording) {
this.policyWording = policyWording;
}

public String getProposalForm() {
return proposalForm;
}

public void setProposalForm(String proposalForm) {
this.proposalForm = proposalForm;
}

public String getClaimForm() {
return claimForm;
}

public void setClaimForm(String claimForm) {
this.claimForm = claimForm;
}

public String getBrochure() {
return brochure;
}

public void setBrochure(String brochure) {
this.brochure = brochure;
}

}