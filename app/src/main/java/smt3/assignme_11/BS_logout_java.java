package smt3.assignme_11;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BS_logout_java extends BottomSheetDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_logout, container, false);
    }
}
