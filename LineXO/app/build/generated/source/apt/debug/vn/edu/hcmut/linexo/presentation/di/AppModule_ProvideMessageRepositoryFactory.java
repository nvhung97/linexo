// Generated by Dagger (https://google.github.io/dagger).
package vn.edu.hcmut.linexo.presentation.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.data.repository.MessageRepository;

public final class AppModule_ProvideMessageRepositoryFactory implements Factory<MessageRepository> {
  private final AppModule module;

  private final Provider<NetworkSource> networkSourceProvider;

  public AppModule_ProvideMessageRepositoryFactory(
      AppModule module, Provider<NetworkSource> networkSourceProvider) {
    this.module = module;
    this.networkSourceProvider = networkSourceProvider;
  }

  @Override
  public MessageRepository get() {
    return provideInstance(module, networkSourceProvider);
  }

  public static MessageRepository provideInstance(
      AppModule module, Provider<NetworkSource> networkSourceProvider) {
    return proxyProvideMessageRepository(module, networkSourceProvider.get());
  }

  public static AppModule_ProvideMessageRepositoryFactory create(
      AppModule module, Provider<NetworkSource> networkSourceProvider) {
    return new AppModule_ProvideMessageRepositoryFactory(module, networkSourceProvider);
  }

  public static MessageRepository proxyProvideMessageRepository(
      AppModule instance, NetworkSource networkSource) {
    return Preconditions.checkNotNull(
        instance.provideMessageRepository(networkSource),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
