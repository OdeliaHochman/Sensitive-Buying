package com.example.sensitivebuying;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Sensitive> updateSensitive= new ArrayList<Sensitive>();
    private DatabaseReference usersReference;
    private Button ButtonSave;
    private FirebaseAuth firebaseAuth;
    private CustomerUser user;
    private View v;

    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_update, container, false);

       // user = (CustomerUser) getIntent().getSerializableExtra("CustomerUser");
        firebaseAuth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference("Users");

        ButtonSave = v.findViewById(R.id.btnsave_registersens_registersens);
        ButtonSave.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == ButtonSave) {
            user.setSensitiveList(updateSensitive);
            usersReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(user);

        }

    }


    public void onCheckboxClicked(View view)
        {
            // Is the view now checked?
            boolean checked = ((CheckBox) view).isChecked();

            // Check which checkbox was clicked
            switch (view.getId()) {
                case R.id.checkBox_eggs_menu: {
                    Sensitive s = new Sensitive(getString(R.string.eggs));
                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_gluten_menu: {
                    Sensitive s = new Sensitive(getString(R.string.gluten));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_lactose_menu: {
                    Sensitive s = new Sensitive(getString(R.string.lactose));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_nuts_menu: {
                    Sensitive s = new Sensitive(getString(R.string.nuts));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_peants_menu: {
                    Sensitive s = new Sensitive(getString(R.string.peanuts));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_soya_menu: {
                    Sensitive s = new Sensitive(getString(R.string.soya));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }
                case R.id.checkBox_sesame_menu: {
                    Sensitive s = new Sensitive(getString(R.string.sesame));

                    if (checked) {
                        updateSensitive.add(s);
                    } else {
                        updateSensitive.remove(s);
                    }
                    break;
                }

            }
        }}




