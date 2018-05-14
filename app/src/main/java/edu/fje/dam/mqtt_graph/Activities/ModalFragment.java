package edu.fje.dam.mqtt_graph.Activities;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franmontiel.fullscreendialog.FullScreenDialogContent;
import com.franmontiel.fullscreendialog.FullScreenDialogController;

import edu.fje.dam.mqtt_graph.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModalFragment extends Fragment implements FullScreenDialogContent {

    private FullScreenDialogController dialogController;

    public ModalFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modal, container, false);
    }


    @Override
    public void onDialogCreated(FullScreenDialogController dialogController) {
            this.dialogController = dialogController;
    }

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialogController) {
        return false;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialogController) {
        return false;
    }
}
