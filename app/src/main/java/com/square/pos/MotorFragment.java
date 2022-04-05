package com.square.pos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MotorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotorFragment newInstance(String param1, String param2) {
        MotorFragment fragment = new MotorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_motor, container, false);
    }
}


//                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
//                    gson.fromJson(data, ChatMessage.class);
//
//                    JsonElement jsonElement = gson.fromJson(data, JsonElement.class);
//                    JsonObject object = jsonElement.getAsJsonObject();
//
//                    System.out.println(object.get("Message"));
//
//                    String str = String.valueOf(object.get("Message"));
//                    Gson gsonData = new GsonBuilder().setPrettyPrinting().create();
//                    gsonData.fromJson(str, MessageData.class);
//
//                    JsonElement mElement = gsonData.fromJson(str, JsonElement.class);
//                    JsonObject mObj = mElement.getAsJsonObject();
//
//                    String senderId = mObj.get("Sender_Id").getAsString();
//                    String type = mObj.get("Type").getAsString();