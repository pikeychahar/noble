package com.dmw.noble.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.dmw.noble.R;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.EndorseViewData;

public class OtherFragment extends Fragment {

    EditText edtNcb, edtFinancierName, edtNomineeName, edtNomineeDob, edtNomineeRelation,
            edtPolicyNo, edtPrePolicyExp, edtPrePolicyNcb, edtPreInsurer;
    Button btnOwner;
    EndorseViewData endorseViewData;
    String ncb, financier, nomineeName, nomineeDob, nomineeRelation, policyNo, prePolicyExp,
            prePolicyNcb, preInsurer, mNcb, mFinancier, mNomineeName, mNomineeDob, mNomineeRelation,
            mPolicyNo, mPrePolicyExp, mPrePolicyNcb, mPreInsurer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_other, container, false);

        edtNcb = root.findViewById(R.id.edtNcb);
        edtFinancierName = root.findViewById(R.id.edtFinancierName);
        edtNomineeName = root.findViewById(R.id.edtNomineeName);
        edtNomineeDob = root.findViewById(R.id.edtNomineeDob);
        edtNomineeRelation = root.findViewById(R.id.edtNomineeRelation);
        edtPolicyNo = root.findViewById(R.id.edtPolicyNo);
        edtPrePolicyExp = root.findViewById(R.id.edtPrePolicyExp);
        edtPrePolicyNcb = root.findViewById(R.id.edtPrePolicyNcb);
        edtPreInsurer = root.findViewById(R.id.edtPreInsurer);

        endorseViewData = CrmManager.getInstance().getEndorseViewData();

        if (endorseViewData != null) {
            mNcb = endorseViewData.getNcb();
            edtNcb.setText(endorseViewData.getNcb());

            mFinancier = endorseViewData.getFinancierName();
            edtFinancierName.setText(endorseViewData.getFinancierName());

            mNcb = endorseViewData.getNcb();
            edtNomineeName.setText(endorseViewData.getNomineeFullName());

            mNomineeDob = endorseViewData.getNomineeFullName();
            edtNomineeDob.setText(endorseViewData.getNomineeDob());

            mNomineeRelation = endorseViewData.getNomineeRelation();
            edtNomineeRelation.setText(endorseViewData.getNomineeRelation());

            mPolicyNo = endorseViewData.getPreviousPolicyNo();
            edtPolicyNo.setText(endorseViewData.getPreviousPolicyNo());

            mPrePolicyExp = endorseViewData.getPreviousPolicyExpiry();
            edtPrePolicyExp.setText(endorseViewData.getPreviousPolicyExpiry());

            mPrePolicyNcb = endorseViewData.getPreviouPolicyNcb();
            edtPrePolicyNcb.setText(endorseViewData.getPreviouPolicyNcb());

            mPreInsurer = endorseViewData.getPreviousPolicyInsurer();
            edtPreInsurer.setText(endorseViewData.getPreviousPolicyInsurer());
        }
        return root;
    }
}