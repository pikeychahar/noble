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

public class OwnerFragment extends Fragment {
    EditText edtSalutation, edtCustomerName, edtPhone, edtMail, edtGender, edtDob, edtAddress1,
            edtLocation, edtPincode, edtCity, edtState, edtPan, edtGst, edtAadhar;
    Button btnOwner;
    EndorseViewData endorseViewData;
    String salutation, name, phone, email, gender, dob, address, location, pincode, city, state,
            pan, gst, aadhar,mSalutation, mName, mPhone, mEmail, mGender, mDob, mAddress, mLocation,
            mPincode, mCity, mState, mPan, mGst, mAadhar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_owner, container, false);

        edtSalutation = root.findViewById(R.id.edtSalutation);
        edtCustomerName = root.findViewById(R.id.edtCustomerName);
        edtPhone = root.findViewById(R.id.edtPhone);
        edtMail = root.findViewById(R.id.edtMail);
        edtGender = root.findViewById(R.id.edtGender);
        edtDob = root.findViewById(R.id.edtDob);
        edtAddress1 = root.findViewById(R.id.edtAddress1);
        edtLocation = root.findViewById(R.id.edtLocation);
        edtPincode = root.findViewById(R.id.edtPincode);
        edtCity = root.findViewById(R.id.edtCity);
        edtState = root.findViewById(R.id.edtState);
        edtPan = root.findViewById(R.id.edtPan);
        edtGst = root.findViewById(R.id.edtGst);
        edtAadhar = root.findViewById(R.id.edtAadhar);

        endorseViewData = CrmManager.getInstance().getEndorseViewData();

        if (endorseViewData != null) {
            mSalutation = endorseViewData.getSalutation();
            edtSalutation.setText(endorseViewData.getSalutation());

            mName = endorseViewData.getFirstName();
            edtCustomerName.setText(endorseViewData.getFirstName());

            mName = endorseViewData.getMobileNumber();
            edtPhone.setText(endorseViewData.getMobileNumber());

            mEmail = endorseViewData.getEmailId();
            edtMail.setText(endorseViewData.getEmailId());

            mGender = endorseViewData.getGender();
            edtGender.setText(endorseViewData.getGender());

            mDob = endorseViewData.getDob();
            edtDob.setText(endorseViewData.getDob());

            mAddress = endorseViewData.getHouseNumber();
            edtAddress1.setText(endorseViewData.getHouseNumber());

            mLocation = endorseViewData.getLocation();
            edtLocation.setText(endorseViewData.getLocation());

            mPincode = endorseViewData.getPincode();
            edtPincode.setText(endorseViewData.getPincode());

            mCity = endorseViewData.getCity();
            edtCity.setText(endorseViewData.getCity());

            mState = endorseViewData.getState();
            edtState.setText(endorseViewData.getState());

            mPan = endorseViewData.getPancardNo();
            edtPan.setText(endorseViewData.getPancardNo());

            mGst = endorseViewData.getGstIn();
            edtGst.setText(endorseViewData.getGstIn());

            mAadhar = endorseViewData.getAadharcardNo();
            edtAadhar.setText(endorseViewData.getAadharcardNo());
        }

        return root;
    }
}