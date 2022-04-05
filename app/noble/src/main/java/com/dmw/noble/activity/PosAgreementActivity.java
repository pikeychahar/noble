package com.dmw.noble.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.dmw.noble.R;
import com.dmw.noble.manager.UserManager;
import com.dmw.noble.model.BasicResponse;
import com.dmw.noble.model_pos.AgentDetail;
import com.dmw.noble.network.ApiClient;
import com.dmw.noble.network.ApiInterface;
import com.dmw.noble.utils.AppUtils;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.XMLReader;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosAgreementActivity extends AbstractActivity {

    private ProgressDialog pd;
    private Context mContext;
    String url = "https://www.squareinsurance.in/Privacypolicy/privacypolicyApp";
    private TextView txtDes;
    String agentId, mDate, posName, address, city, state, pincode, age, aadhar;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_agreement);
        this.setTitle("POS Agreement");

        mContext = this;
        pd = new ProgressDialog(mContext);

        pd.setMessage("Wait a moment...");
        preferences = mContext.getSharedPreferences(String.valueOf(R.string.app_name), MODE_PRIVATE);
        agentId = preferences.getString(AppUtils.PRIMARY_ID, "");

        if (!TextUtils.isEmpty(agentId))
            getAgentDetail();

        String pattern = "dd-MM-yyyy";
        mDate = new SimpleDateFormat(pattern).format(new Date());
        txtDes = findViewById(R.id.webView);
    }

    public void getAgentDetail() {
        pd.show();
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<AgentDetail> call = apiService.getAgentDetail(agentId,"agent");
            call.enqueue(new Callback<AgentDetail>() {
                @Override
                public void onResponse(Call<AgentDetail> call, Response<AgentDetail> response) {
                    if (response.isSuccessful()) {
                        AgentDetail commonResponse = response.body();
                        if (commonResponse != null) {

                            posName = commonResponse.getName();
                            city = commonResponse.getCity();
                            state = commonResponse.getState();
                            pincode = commonResponse.getPincode();
                            aadhar = commonResponse.getAadharcardNo();
                            String dob = commonResponse.getDob();

                            if (!TextUtils.isEmpty(dob)) {
                                age = "" + AppUtils.getAge(dob);
                            }
                            address = commonResponse.getAddress1() + " " + commonResponse.getAddress2() + " " + commonResponse.getAddress3();

                            url = "<h4>Agreement of Appointment and engagement as Point of Sales (POS)</h4>\n" +
                                    "\n" +
                                    "                <p>THIS AGREEMENT is made on the " + mDate + "BETWEEN " +
                                    "                    <p>Square Insurance Brokers Private Limited (\"Company\")(CIN- U66000RJ2016PTC056324) a company registered under Companies Act,2013 having its registered office at Unit No 506, 5th Floor Trimurty V-Jai City Point Ahimsa Circle, C-Scheme Jaipur-302001, Rajasthan (hereinafter referred to as the “Company” ) AND</p>\n" +
                                    "                    <p>Shri " + posName + "," + address + " " + city + " " + state + " " + pincode + "" + " aged about " + age + " years" + " holder of Aadhar Number " + aadhar + " here in after referred to as “Point Of Sales Person”or \"POS\"</p>\n" +
                                    "\n" +
                                    "                    <p>Whereas Insurance Regulatory and Development Authority of India (hereinafter called as IRDAI) has issued guidelines for appointment as POS vide circular no. IRDA/Int/GDL/ORD/183/10/2015 dated 26th October 2015, followed by IRDAI circular; IRDA/Int/GDL/ORD/057/03/2016 dated 11/3/2016 amended by CIRCULAR NO.IRDA/INT/GDL/PSP/058/03/2017, DATED 16-3-2017. </p>\n" +
                                    "                    <p>WHEREAS, Company is a Direct Insurance Broker registered by IRDAI vide registration No 606 valid w.e.f 05/07/2017 to 04/07/2020 and renewable thereafter from time to time.</p>\n" +
                                    "                    <p>WHEREAS, POS has applied to Company for getting associated with it for procuring and distributing Insurance Products in which company deals and has qualified the requisite examination conducted by the Company as per the IRDA Guidelines. WHEREAS, The Company appoints the POS for the purpose of selling and servicing of Insurance policies on behalf of the Company.</p>\n" +
                                    "\n" +
                                    "                    <p>The Company reserves the right to approve or disapprove the contracting of any such POS. The Company and the POS expressly agree that the POS is not an employee of the Company and shall be considered an independent contractor for the purposes of this agreement. The POS shall not be reimbursed of any expenses incurred under this agreement and shall supply his or her own work place, use his or her own supplies and set his or her own work hours, all at no cost to the Company.</p>\n" +
                                    "\n" +
                                    "                    <p>NOW, THEREFORE, in consideration of the mutual promises and covenants herein stated, and for other good and valuable consideration, the parties hereto agree as follows:</p>\n" +
                                    "\n" +
                                    "\n" +
                                    "                    <h4> 1. APPOINTMENT</h4>\n" +
                                    "\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The Company hereby appoints POS to solicit, at its own expense, new and/or renewal applications for insurance contracts (\"Policy\" or \"Policies\") as listed in Annexure 1, as amended from time to time. No product shall be solicited by the POS other than the list mentioned in Annexure 1.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "\n" +
                                    "                    <h4>2. DUTIES OF POS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The POS shall comply with the terms of this agreement</li>\n" +
                                    "                        <li>POS will comply with all laws and regulations which relate to this Agreement and shall indemnify and hold the Company harmless for its failure to do so. POS shall maintain in good standing, at its own cost, licenses required by all applicable statutes and regulations</li>\n" +
                                    "                        <li>POS may not solicit any business except mentioned in schedule \"A\"</li>\n" +
                                    "                        <li>POS will comply with the Company's rules and regulations relating to the Soliciting the insurance business. As a material part of the consideration for the making of this Agreement by the Company, POS agrees that there will be made no representations whatsoever with respect to the nature or scope of the benefits of the Policies sold except through and by means of the written material either prepared and furnished to POS for that purpose by the Company or approved in writing by the Company prior to its use. POS shall have no authority and will not make any oral or written alteration, modification, or waiver of any of the terms or conditions of any Policy whatsoever.</li>\n" +
                                    "                        <li>POS warrants that POS will diligently and to the best of its ability ensure that the facts set forth by any applicant/prospect in any application it solicits are true and correct.</li>\n" +
                                    "                        <li>POS will conduct itself so as not to affect adversely the business, good standing, and reputation of the Company.</li>\n" +
                                    "                        <li>\n" +
                                    "                            POS agrees not to employ or make use of any advertisement in which the Company's (or its affiliate's) name or its registered trademarks are employed without the prior written approval and consent of the Company. Upon request of POS during the term of this Agreement, the Company shall make available for POS's use, standard visiting cards and other material. POS may add, at POS's expense, to the standard advertising only its business name, business address, POS number and telephone number, as provided for in the advertising. No deletions or changes in the advertising copy are permissible.\n" +
                                    "                            POS shall act solely as an independent contractor, of-course subject to the control and guidance of the company, and as such, shall have control on: all matters, its time and effort in the placement of the Policies offered hereunder. Nothing herein contained shall be construed to create the relationship of employer and employee between POS and Company.\n" +
                                    "                        </li>\n" +
                                    "                        <li>POS shall indemnify and hold the Company and its officers, POSs and employees harmless from all expenses, costs, causes of action, claims, demands, liabilities and damages, including reasonable attorney's fees, resulting from or growing out of any unauthorized act or transaction or any negligent act, omission or transaction by POS or employees of POS.</li>\n" +
                                    "                        <li>Solicitation of Business.\n" +
                                    "                            <p>POS is authorized to solicit the Insurance Business mentioned in Annexure 1 and as per additional description and narration as may be advised by the company.</p>\n" +
                                    "                        </li>\n" +
                                    "                        <li> No Prior Disciplinary or Criminal Proceedings.\n" +
                                    "                            <p>POS represents and warrants that he/she has never been convicted of any crime involving moral turpitude and is not disqualified as per section 42D(5) of the Insurance Act and remains Fit and Proper as per the format enclosed herewith in Annexure 2 of this agreement.</p>\n" +
                                    "                        </li>\n" +
                                    "                        <li>Change of Address. POS shall notify Company in writing of any change of address and/or communication at least thirty (30) days prior to the effective date of such change.</li>\n" +
                                    "                        <li>POS shall not engage or employ anyone as canvassers or agents for soliciting the insurance business.</li>\n" +
                                    "                        <li>Collection of Premiums. POS shall have no authority, without written permission of Company, to collect or provide receipt for premiums to customer and shall assist the client for compliance of section 64VB of the Insurance Act 1938.</li>\n" +
                                    "                        <li>Other Expenses. POS shall have no claim or shall not be entitled to reimbursement for any expenses.</li>\n" +
                                    "                        <li>POS shall, on behalf of the Company, collect premiums as per IRDAI norms. All premiums collected on business produced by the POS hereunder shall be submitted to the Company within same day of receipt by POS.</li>\n" +
                                    "                        <li>To faithfully perform all duties required hereunder, to cooperate with the Company in all matters pertaining to the issuance of policies, cancellations, claims and to promote the best interest of the Company.</li>\n" +
                                    "                        <li>POS will be bound not to work for any other intermediaries or the Insurance companies. Whatever work he does in the insurance space, POS is bound to do through Company only.</li>\n" +
                                    "                        <li>POS will ensure the compliance of FIU and obtains KYC </li>\n" +
                                    "                        <li>POS shall not do any claim consultancy and any such opportunity that comes in this area. He shall be further obliged to bring to the notice of the company for its further doing the needful in a professional manner.</li>\n" +
                                    "                        <li>Any financial penalty levied by the IRDAI, if it is based on the violations and non compliance by the POS shall be borne by him. Similarly if suspension, cancellation or withdrawal of license of the company is based on breaches/non compliance on the account of POS, the POS shall indemnify the consequential losses to the company.</li>\n" +
                                    "                        <li> The POS shall be duty bound to cooperate with the officers of IRDAI for the purpose of inspection as may be required by IRDAI inspectors or investigating authority from time to time.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "\n" +
                                    "                    <h4>3. DUTIES OF THE COMPANY</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The duties of the Company shall vary depending upon the specific product being sold by POS. For all products, the Company will provide brochures and proposal forms. The Company will deliver to the customer all insurance policies and related correspondence or similar documents, in accordance with Company procedures. The Company shall\n" +
                                    "                            Respond in a reasonable and timely manner to inquiries and questions about the product. The Company shall maintain reasonable accounting, administrative, and statistical records in accordance with prudent standards of insurance record keeping, including premium, sale or effective date, and any other records needed to verify coverage, pay claims, or underwrite the company insurance products, of any insured participant covered under the policies.</li>\n" +
                                    "                        <li>The company shall provide administrative support including infrastructure to the POS to enable him to perform his obligation on the interest of Broking Company and the customer whose business has been solicited.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>4. RESERVATION OF RIGHTS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The Company reserves the right to reject any and all applications for its Policies submitted by POS if they are not found to be of the order of merit required by the customer or the company or the Insurance Company.</li>\n" +
                                    "                        <li>The Company reserves the right to discontinue writing or offering any of the Policies which become subject to this Agreement upon sixty (60) days notice to POS (or the number of days required by law in the POS's state of domicile).</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>5. MAINTENANCE OF RECORDS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Company and POS each shall maintain records of transactions with individual insureds.</li>\n" +
                                    "                        <li>The Company, its employees, or authorized representatives may have unrestricted access to records and may audit, inspect and examine at reasonable times, upon reasonable notice and during regular business hours at POS's place of business, all books and records.</li>\n" +
                                    "                        <li>During the term of the Agreement, any extensions of it and for five (5) years thereafter, the Company shall keep strictly secret and confidential any Confidential Information about POS, and POS shall keep strictly secret and confidential any Confidential Information about the Company. \"Confidential Information\" shall, amongst other necessary matter/s, include: information, written or unwritten, which pertains in any way to financial or accounting matters, business production, methods of business operations, marketing, strategic planning or proprietary information of any kind or nature whatsoever, including trade secrets or know-how. Confidential Information does not include information that: (i) is already known to the recipient at the time of disclosure to it; (ii) is in the public domain or subsequently becomes publicly available; (iii) is provided to the recipient by a third party who is under no such obligation of confidentiality; or (iv) is independently developed by the recipient. Each party shall take necessary and reasonable precautions to prevent unauthorized disclosure of Confidential Information and shall require all of its officers, employees, and other personnel to whom it is necessary to disclose the same, or to whom the same has been disclosed, to keep this Confidential Information secret and confidential. It is understood, however, that certain \"Confidential Information\" may be required to be filed with State and Central regulatory agencies in accordance with their reporting requirements. Neither party shall make use of the name or service mark(s) of the other, including use of the name or service mark(s) of any marketing, enrollment, or other public relations material without prior written approval of the other party.</li>\n" +
                                    "                        <li>There shall be restriction on use of Square Insurance Brokers Private Limited logo and letterhead by POS unless approved by Square Insurance Brokers Private Limited in writing.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>6 COMPENSATION</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Compensation for each policy written hereunder shall be made by the Company to POS in accordance with Annexure 2, attached hereto and incorporated herein by reference.</li>\n" +
                                    "                        <li>Compensation due under this Agreement shall be paid to POS within 30 days from the end of each calendar quarter in which the Company receives premium with respect to its Policies or as may otherwise be agreed upon in writing.</li>\n" +
                                    "                        <li> Under all circumstances, the Company shall have the right to offset overpayments to POS against amounts due to POS.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>7. EFFECTIVE DATE, TERM AND TERMINATION</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>This Agreement shall commence on the effective date first stated above and shall continue in force until terminated pursuant to this Article. Upon termination, all business produced by the POS shall remain in full force and effect until the natural expiration or prior cancellation of such business, and shall be subject to all terms and conditions of this Agreement. Upon termination, the all data related to policy holder will be handed over to company by POS</li>\n" +
                                    "                        <li>This Agreement will terminate automatically upon the occurrence of any of the following events by POS, and upon such occurrence the parties shall be obligated to make only those payments the right to which accrued to the date of termination:\n" +
                                    "                            <p>Conviction of a felony by POS;</p>\n" +
                                    "                            <p>Misappropriation of (or failure to remit) any funds or property due to the Company;</p>\n" +
                                    "                            <p>Determination that POS is not working in compliance with Company underwriting guidelines or the terms of this Agreement and POS has failed to correct the problem within 10 days of the Company providing written notice of same;</p>\n" +
                                    "                            <p>In the event of fraud or material breach of any of the conditions or provisions of this Agreement on the part of either party, the other party may terminate the Agreement immediately upon written notice.</p>\n" +
                                    "                            <p>Fails to comply with directions of Square Insurance Brokers Private Limited.</p>\n" +
                                    "                            <p>Furnish wrong information or conceals the information or fails to disclose the material facts of the policy to the policy holder.</p>\n" +
                                    "                            <p>Fails to resolve complaints, unless the circumstances are beyond his control, emanating from the business procured by him and persons he deals with</p>\n" +
                                    "                            <p>Indulges in inducement in cash or kind with client or any other insurance intermediary /agent /insurer.</p>\n" +
                                    "                            <p>Fails to pay any penalty levied on his account.</p>\n" +
                                    "                        </li>\n" +
                                    "                        <li>Fails to carry out his obligations as prescribed in the agreement and in the provisions of: Act/regulations/circulars or guidelines by IRDAI from time to time.\n" +
                                    "                            <p>Acts in a manner prejudice to the interest of the company or the client</p>\n" +
                                    "                            <p>Acts in a manner that amounts to diverting funds of his Group/Affiliates or associates rather than engaging in the activity of soliciting and servicing insurance business</p>\n" +
                                    "                            <p>Is found guilty of fraud or is charged or convicted in any criminal act.</p>\n" +
                                    "                        </li>\n" +
                                    "                        <li>In the event of a material breach by a party to this Agreement, the non-breaching party may terminate this Agreement after providing thirty (30) days written notice to the breaching party to cure such breach. Upon such occurrence, a party shall be obligated to make only those payments the right which accrued to the date of termination.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>8. GENERAL PROVISIONS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Failure of either party to insist upon the performance of any of the terms of this Agreement or to declare a forfeiture or termination in the event of non-performance by the other party shall not constitute a waiver of performance required hereunder.</li>\n" +
                                    "                        <li>No assignment, transfer or disposal of any interest that a party may have pursuant to this Agreement shall be made at any time without prior written approval of the other party. Notwithstanding the foregoing, Company may assign any and all interests under this Agreement to a parent or affiliate, or due to merger or acquisition without the consent of POS.</li>\n" +
                                    "                        <li>This Agreement shall be binding upon the administrators and executors, successors and permitted assignees of the parties hereto.</li>\n" +
                                    "                        <li>No Amendment or modification of this Agreement shall be valid, or of any force or effect, unless the same be in writing and acknowledged and signed by the Company and POS.</li>\n" +
                                    "                        <li>Any disputes, claims or counterclaims arising from or relating to this Agreement shall be subject to the Jurisdiction of the courts in Mumbai, India with first preference exercised to resolve by Alternative dispute resolution Indian system.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>9. INTELLECTUAL PROPERTY</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The POS agrees, warrants and undertakes that it shall take steps to safeguard AARVI’s intellectual property rights, if in its possession or AARVI’s Products, Services and software are not infringed, passed off, diluted, reverse-engineered, hacked into, misappropriated, tampered with and/or copied by the POS, and/or its directors, officers, employees, agents, representatives, subsidiaries, associates, etc.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>10. INDEMNITY</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>The POS, shall at its own expense, indemnify, defend and hold harmless Square Insurance Brokers Pvt. Ltd. and its officers, directors, employees, representatives and agents, against any third party claim, demand, suit, action, or other proceeding brought against Square Insurance Brokers Pvt. Ltd. or its officers, directors, employees, representatives or agents and against all damages, awards, settlements, liabilities, losses, costs and expenses related thereto (including without limitation attorneys’ fees) to the extent that such claim suit, action or other proceedings is based on or arises from any deficiency in service, by the POS, as per the scope of work as detailed in Annexure 1 hereto or any other breach of terms of this Agreement.</li>\n" +
                                    "                    </ul>\n" +
                                    "                    <h4>11. COMPLIANCE WITH THE LAWS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Each party represents that it shall abide by and observes all applicable laws, rules, regulations.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>12. GOVERNING LAW AND DISPUTE RESOLUTION</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Any dispute, controversy or claims arising out of or relating to this Agreement or the breach, termination or invalidity thereof, shall be settled by arbitration in accordance with the provisions of the Arbitration and Conciliation Act, 1996. The Arbitral Tribunal shall compose of a sole arbitrator to be appointed by both the Parties in mutual consent. The place of arbitration shall be Mumbai and any award whether interim or final, shall be made, and shall be deemed for all purposes between the parties to be made, in Mumbai. The arbitral procedure shall be conducted in the English language and any award or awards shall be rendered in English. The provisions of this Agreement shall be governed by and construed in accordance with Indian law. Only the courts at Mumbai shall have exclusive jurisdiction in all matters arising under this Agreement</li>\n" +
                                    "                    </ul>\n" +
                                    "                    <h4>13. SEVERABILITY</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>If any provision of this Agreement is held illegal or unenforceable by any court or other authority of competent jurisdiction, such provision shall be deemed severable from the remaining provisions of this Agreement and shall not affect or impair the validity or enforceability of the remaining provision of this Agreement.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>14. FORCE MAJEURE</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li>Neither Party shall be under any liability for any failure to perform any of its obligations under this Agreement due to Force Majeure. For the purpose of this clause, “Force Majeure” means fire, explosion, flood, Act of God, act of terrorism, war, rebellion, riots, or sabotage or events or circumstances which are wholly outside the control of the party affected thereby. Where, such event continues to exist for a continuous period of 3 months or more, the Parties hereby agrees that the Agreement shall stand terminated.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <h4>15. Training and qualification requirement for POS</h4>\n" +
                                    "                    <ul>\n" +
                                    "                        <li> The POS person shall be atleast 10th pass or attain any other qualification IRDAI may prescribe from time to time.</li>\n" +
                                    "                        <li>The POS has to undertake fifteen (15) hours compulsory training as per the guidelines of IRDAI as arranged/provided by the Company. </li>\n" +
                                    "                        <li>After the completion of the respective training, the POS has to pass the examination conducted by the Company. </li>\n" +
                                    "                        <li>The respective POS who have successfully cleared the examination shall be issued the completion certificate.</li>\n" +
                                    "                    </ul>\n" +
                                    "\n" +
                                    "                    <p>This Agreement constitutes the entire Agreement between the parties with respect to its matter.</p>\n" +
                                    "                    <hr>";

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                txtDes.setText(Html.fromHtml(url, null, new MyTagHandler1()));
                            } else {
                                txtDes.setText(Html.fromHtml(url, null, new MyTagHandler1()));
                            }
                        }
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<AgentDetail> call, Throwable t) {
                    //onResponse(t);
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAgreementClick(View view) {
        submitAgreement();

    }

    public void submitAgreement() {
        pd.show();
        ApiClient restClient = new ApiClient(mContext);
        restClient.setCommunication();
        ApiInterface apiService = restClient.getApiService();

        try {
            Call<BasicResponse> call = apiService.posAgreement(agentId);
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(@NotNull Call<BasicResponse> call, @NotNull Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        BasicResponse commonResponse = response.body();
                        if (commonResponse != null) {
                            if (commonResponse.getStatus() == 1) {
                                preferences.edit().putString(AppUtils.IS_AGREEMENT_POS, "1").apply();
                                startActivity(new Intent(mContext, DashboardActivity.class));
                                finishAffinity();
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAgreementCancelClick(View view) {
        preferences.edit().clear().apply();
        AgentDetail agentDetail = new AgentDetail();
        UserManager.getInstance().setAgentDetail(agentDetail);
        preferences.edit().putBoolean(AppUtils.IS_FIRST_TIME,
                false).apply();
        startActivity(new Intent(mContext, MainActivity.class));
        finishAffinity();

    }

    @Override
    public void onBackPressed() {
        return;
    }
}

class MyTagHandler1 implements Html.TagHandler {

    boolean first = true;
    String parent = null;
    int index = 1;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        if (tag.equals("ul")) {
            parent = "ul";
        } else if (tag.equals("ol")) {
            parent = "ol";
        }

        if (tag.equals("li")) {
            if (parent.equals("ul")) {
                if (first) {
                    output.append("\n\t•");
                    first = false;
                } else {
                    first = true;
                }
            } else {
                if (first) {
                    output.append("\n\t" + index + ". ");
                    first = false;
                    index++;
                } else {
                    first = true;
                }
            }
        }
    }
}