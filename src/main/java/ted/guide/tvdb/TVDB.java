package ted.guide.tvdb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import ted.TedIO;
import ted.guide.tvdb.Mirrors.NoMirrorException;

public class TVDB
{
    // TODO: add logger
    private static final String apikey = "0D513FDFA9D09C21";

    private static class TVDBHolder
    {
        private static final TVDB INSTANCE = new TVDB();
    }

    public static TVDB getInstance()
    {
        return TVDBHolder.INSTANCE;
    }

    private TVDB()
    {
        URL mirrorUrl;
        try
        {
            mirrorUrl = new URL("http://www.thetvdb.com/api/" + apikey + "/mirrors.xml");
            URLConnection mirrorsConnection = TedIO.makeUrlConnection(mirrorUrl);

            Mirrors.createMirrors(mirrorsConnection.getInputStream());
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (NoMirrorException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
