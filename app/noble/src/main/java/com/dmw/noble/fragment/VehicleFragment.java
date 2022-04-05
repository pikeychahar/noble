package com.dmw.noble.fragment;

import  android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.dmw.noble.R;
import com.dmw.noble.manager.CrmManager;
import com.dmw.noble.model_crm.EndorseViewData;

public class VehicleFragment extends Fragment {
    EditText edtRegYear, edtMake, edtModel, edtVariant, edtFuel, edtEngine, edtChassis, edtCubic,
            edtCNGIdv, edtPa, edtUnnamed, edtLegal, edtLegalEmp;
    Button btnOwner;
    EndorseViewData endorseViewData;
    String regYear, make, model, variant, fuel, cubic, engine, chassis, cngIdv, pa, unnamed, legal,
            legalEmp, mRegYear, mMake, mModel, mVariant, mFuel, mCubic, mEngine, mChassis, mCngIdv,
            mPa, mUnnamed, mLegal, mLegalEmp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vehicle, container, false);
        edtRegYear = root.findViewById(R.id.edtRegYear);
        edtMake = root.findViewById(R.id.edtMake);
        edtModel = root.findViewById(R.id.edtModel);
        edtVariant = root.findViewById(R.id.edtVariant);
        edtFuel = root.findViewById(R.id.edtFuel);
        edtEngine = root.findViewById(R.id.edtEngine);
        edtChassis = root.findViewById(R.id.edtChassis);
        edtCubic = root.findViewById(R.id.edtCubic);
        edtCNGIdv = root.findViewById(R.id.edtCNGIdv);
        edtPa = root.findViewById(R.id.edtPa);
        edtUnnamed = root.findViewById(R.id.edtUnnamed);
        edtLegal = root.findViewById(R.id.edtLegal);
        edtLegalEmp = root.findViewById(R.id.edtLegalEmp);

        endorseViewData = CrmManager.getInstance().getEndorseViewData();

        if (endorseViewData != null) {

            mRegYear = endorseViewData.getRegistrationYear();
            edtRegYear.setText(endorseViewData.getRegistrationYear());

            mMake = endorseViewData.getMake();
            edtMake.setText(endorseViewData.getMake());

            mModel = endorseViewData.getModel();
            edtModel.setText(endorseViewData.getModel());

            mVariant = endorseViewData.getVariant();
            edtVariant.setText(endorseViewData.getVariant());

            mFuel = endorseViewData.getFuelType();
            edtFuel.setText(endorseViewData.getFuelType());

            mEngine = endorseViewData.getEngineNo();
            edtEngine.setText(endorseViewData.getEngineNo());

            mChassis = endorseViewData.getChassisNo();
            edtChassis.setText(endorseViewData.getChassisNo());

            mCubic = endorseViewData.getCubicCapacity();
            edtCubic.setText(endorseViewData.getCubicCapacity());

            mCngIdv = endorseViewData.getCngIdv();
            edtCNGIdv.setText(endorseViewData.getCngIdv());

            mPa = endorseViewData.getPaOwnerDriver();
            edtPa.setText(endorseViewData.getPaOwnerDriver());

            mUnnamed = endorseViewData.getUnnamedPa();
            edtUnnamed.setText(endorseViewData.getUnnamedPa());

            mLegal = endorseViewData.getLegalLiability();
            edtLegal.setText(endorseViewData.getLegalLiability());

            mLegalEmp = endorseViewData.getLegalLiabilityForEmp();
            edtLegalEmp.setText(endorseViewData.getLegalLiabilityForEmp());
        }
        return root;
    }
}