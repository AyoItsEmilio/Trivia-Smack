package comp4350.triviasmack.application;

import comp4350.triviasmack.business.AsyncFacade;
import comp4350.triviasmack.business.AsyncFacadeObject;
import comp4350.triviasmack.business.ServerAccess;
import comp4350.triviasmack.business.ServerAccessObject;


public class Services {
    private static ServerAccess serverAccessService = null;
    private static AsyncFacade asyncService = null;

    public static ServerAccess createServerAccess() {
        if (serverAccessService == null) {
            serverAccessService = new ServerAccessObject();
            serverAccessService.open();
        }
        return serverAccessService;
    }

    public static ServerAccess createServerAccess(ServerAccess alternateServerAccessService) {
        if (serverAccessService == null) {
            serverAccessService = alternateServerAccessService;
            serverAccessService.open();
        }
        return serverAccessService;
    }

    public static ServerAccess getServerAccess() {
        return serverAccessService;
    }

    public static void closeServerAccess() {
        if (serverAccessService != null) {
            serverAccessService.close();
        }
        serverAccessService = null;
    }

    public static AsyncFacade createAsyncFacade() {
        if (asyncService == null) {
            asyncService = new AsyncFacadeObject();
        }
        return asyncService;
    }

    public static AsyncFacade createAsyncFacade(AsyncFacade alternateAsyncService) {
        if (asyncService == null) {
            asyncService = alternateAsyncService;
        }
        return asyncService;
    }

    public static AsyncFacade getAsyncFacade() {
        return asyncService;
    }
}
