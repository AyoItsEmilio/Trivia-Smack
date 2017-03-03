package comp4350.triviasmack.application;

import comp4350.triviasmack.business.ServerAccess;

import comp4350.triviasmack.business.ServerAccessObject;


public class Services {
    private static ServerAccess serverAccessService = null;

    public static ServerAccess createServerAccess(int numQuestions) {
        if (serverAccessService == null) {
            serverAccessService = new ServerAccessObject(numQuestions);
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
}
