package vn.edu.hcmut.linexo.presentation.di;

import android.content.Context;
import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import vn.edu.hcmut.linexo.data.cache.CacheSource;
import vn.edu.hcmut.linexo.data.cache.SharedPreferencesDB;
import vn.edu.hcmut.linexo.data.local.LocalSource;
import vn.edu.hcmut.linexo.data.local.RoomDB;
import vn.edu.hcmut.linexo.data.network.FirebaseDB;
import vn.edu.hcmut.linexo.data.network.NetworkSource;
import vn.edu.hcmut.linexo.data.repository.BoardRepository;
import vn.edu.hcmut.linexo.data.repository.BoardRepositoryImpl;
import vn.edu.hcmut.linexo.data.repository.RoomRepository;
import vn.edu.hcmut.linexo.data.repository.RoomRepositoryImpl;
import vn.edu.hcmut.linexo.data.repository.SessionRepository;
import vn.edu.hcmut.linexo.data.repository.SessionRepositoryImpl;
import vn.edu.hcmut.linexo.data.repository.UserRepository;
import vn.edu.hcmut.linexo.data.repository.UserRepositoryImpl;
import vn.edu.hcmut.linexo.domain.interactor.PlayUsecase;
import vn.edu.hcmut.linexo.domain.interactor.RoomUsecase;
import vn.edu.hcmut.linexo.domain.interactor.Usecase;
import vn.edu.hcmut.linexo.presentation.view_model.play.PlayViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.room.RoomViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.ViewModel;
import vn.edu.hcmut.linexo.presentation.view_model.splash.SplashViewModel;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public CacheSource provideCacheSource(Context context){
        return new SharedPreferencesDB(context);
    }

    @Provides
    public NetworkSource provideNetworkSource(){
        return new FirebaseDB();
    }

    @Provides
    public LocalSource provideLocalSource(){
        return new RoomDB();
    }

    @Provides
    @Singleton
    public SessionRepository provideSessionRepository(CacheSource cacheSource) {
        return new SessionRepositoryImpl(cacheSource);
    }

    @Provides
    @Singleton
    public BoardRepository provideBoardRepository(NetworkSource networkSource) {
        return new BoardRepositoryImpl(networkSource);
    }

    @Provides
    @Singleton
    public UserRepository provideUserRepository(NetworkSource networkSource, CacheSource cacheSource, LocalSource localSource) {
        return new UserRepositoryImpl(networkSource, cacheSource, localSource);
    }

    @Provides
    @Singleton
    public RoomRepository provideRoomRepository(NetworkSource networkSource) {
        return new RoomRepositoryImpl(networkSource);
    }

    @Provides
    @Named("PlayUsecase")
    public Usecase providePlayUsecase(SessionRepository sessionRepository,
                                      BoardRepository boardRepository,
                                      UserRepository userRepository) {
        return new PlayUsecase(sessionRepository, boardRepository, userRepository);
    }

    @Provides
    @Named("RoomUsecase")
    public Usecase provideRoomUsecase(UserRepository userRepository, RoomRepository roomRepository) {
        return new RoomUsecase(userRepository, roomRepository);
    }

    @Provides
    @Named("SplashViewModel")
    public ViewModel provideSplashViewModel() {
        return new SplashViewModel();
    }

    @Provides
    @Named("RoomViewModel")
    public ViewModel provideRoomViewModel(Context context, @Named("RoomUsecase") Usecase roomUsecase) {
        return new RoomViewModel(context, roomUsecase);
    }

    @Provides
    @Named("PlayViewModel")
    public ViewModel providePlayViewModel(@Named("PlayUsecase") Usecase playUsecase) {
        return new PlayViewModel(playUsecase);
    }
}
