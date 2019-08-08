package pontini.systems.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class BaseDialogFragment extends DialogFragment {

    private boolean firstSession = true;
    private String message;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isFirstSession()) {
            onResumeFirstSession();
            setFirstSession(false);
        } else {
            onResumeAfterRestart();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        bindMessage(alert);
        bindTitle(alert);
        bindButtons(alert);

        return alert.create();
    }



    protected void bindButtons(AlertDialog.Builder alert) {
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    protected void bindMessage(AlertDialog.Builder alert) {
        if (!getMessage().isEmpty()) {
            alert.setMessage(getMessage());
        }
    }

    protected void bindTitle(AlertDialog.Builder alert) {
        if (!getTitle().isEmpty()) {
            alert.setTitle(getTitle());
        }
    }

    protected void onResumeFirstSession() {
    }

    protected void onResumeAfterRestart() {
    }

    public boolean isFirstSession() {
        return firstSession;
    }

    public void setFirstSession(boolean firstSession) {
        this.firstSession = firstSession;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
