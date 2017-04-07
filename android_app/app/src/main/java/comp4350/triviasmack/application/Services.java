package comp4350.triviasmack.application;

import comp4350.triviasmack.business.AsyncFetchFacade;
import comp4350.triviasmack.business.FetchFacade;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.business.ServerAccessObject;


public class Services {
    private static ServerAccess serverAccessService = null;
    private static FetchFacade asyncService = null;

    public static ServerAccess createServerAccess() {
        if (serverAccessService == null) {
            serverAccessService = new ServerAccessObject();
        }
        return serverAccessService;
    }

    public static ServerAccess createServerAccess(ServerAccess alternateServerAccessService) {
        if (serverAccessService == null) {
            serverAccessService = alternateServerAccessService;
        }
        return serverAccessService;
    }

    public static ServerAccess getServerAccess() {
        return serverAccessService;
    }

    public static void closeServerAccess() {
        serverAccessService = null;
    }

    public static FetchFacade createAsyncFacade() {
        if (asyncService == null) {
            asyncService = new AsyncFetchFacade();
        }
        return asyncService;
    }

    public static FetchFacade createAsyncFacade(FetchFacade alternateAsyncService) {
        if (asyncService == null) {
            asyncService = alternateAsyncService;
        }
        return asyncService;
    }

    public static FetchFacade getAsyncFacade() {
        return asyncService;
    }
}
