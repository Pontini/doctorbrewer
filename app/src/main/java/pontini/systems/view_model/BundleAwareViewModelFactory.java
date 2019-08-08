package pontini.systems.view_model;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

public class BundleAwareViewModelFactory<T extends ParcelableViewModel> implements ViewModelProvider.Factory {
    private final Bundle bundle;
    private final ViewModelProvider.Factory provider;

    public BundleAwareViewModelFactory(@Nullable Bundle bundle,
                                       ViewModelProvider.Factory provider) {
        this.bundle = bundle;
        this.provider = provider;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T create(final Class modelClass) {
        T viewModel = (T) provider.create(modelClass);
        if (bundle != null) {
            viewModel.onReceiveBundle(bundle);
        }
        viewModel.postReceiveBundle();
        return viewModel;
    }
}