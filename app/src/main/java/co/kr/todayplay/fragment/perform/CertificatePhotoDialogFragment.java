package co.kr.todayplay.fragment.perform;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import co.kr.todayplay.R;

public class CertificatePhotoDialogFragment extends DialogFragment {
    private Fragment parentFragment;
    private ConstraintLayout ticket_btn, receipt_btn, etc_btn;
    private Button save_btn;

    public CertificatePhotoDialogFragment (){}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pop_up_certificate_photo,null);
        ticket_btn = (ConstraintLayout)view.findViewById(R.id.ticket_btn);
        receipt_btn = (ConstraintLayout)view.findViewById(R.id.receipt_btn);
        etc_btn = (ConstraintLayout)view.findViewById(R.id.etc_btn);
        save_btn = (Button)view.findViewById(R.id.save_btn);

        builder.setView(view);

        return builder.create();
    }
}
