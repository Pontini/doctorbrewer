package pontini.systems.view_model;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class ParcelableViewModel extends AndroidViewModel {

    //public abstract void writeTo(@NonNull Bundle bundle);
    public abstract void onReceiveBundle(@NonNull Bundle bundle);

    public abstract void postReceiveBundle();

    public ParcelableViewModel(@NonNull Application application) {
        super(application);
    }

}