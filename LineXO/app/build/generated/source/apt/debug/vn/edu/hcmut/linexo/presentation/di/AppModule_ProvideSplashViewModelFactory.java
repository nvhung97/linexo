// Generated by Dagger (https://google.github.io/dagger).
package vn.edu.hcmut.linexo.presentation.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;

public final class AppModule_ProvideSplashViewModelFactory implements Factory<ViewModel> {
  private final AppModule module;

  public AppModule_ProvideSplashViewModelFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public ViewModel get() {
    return provideInstance(module);
  }

  public static ViewModel provideInstance(AppModule module) {
    return proxyProvideSplashViewModel(module);
  }

  public static AppModule_ProvideSplashViewModelFactory create(AppModule module) {
    return new AppModule_ProvideSplashViewModelFactory(module);
  }

  public static ViewModel proxyProvideSplashViewModel(AppModule instance) {
    return Preconditions.checkNotNull(
        instance.provideSplashViewModel(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
