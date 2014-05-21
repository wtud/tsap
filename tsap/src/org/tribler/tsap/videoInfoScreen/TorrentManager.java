package org.tribler.tsap.videoInfoScreen;

import java.util.ArrayList;

import org.tribler.tsap.R;
import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;
import org.tribler.tsap.thumbgrid.ThumbItem;

/**
 * Used to retrieve Torrent objects by their ID
 * @author Niels Spruit
 */
public class TorrentManager {
	private static ArrayList<Torrent> torrents;
	
	/**
	 * Initializes the torrent list with some torrent stubs
	 */
	public static void initializeTorrents()
	{
		torrents = new ArrayList<Torrent>();
		torrents.add(new Torrent("Sintel","Video","2010-06-08",400.51,73,3,TORRENT_HEALTH.GREEN,"Sintel is a short computer animated film by the Blender Institute, part of the Blender Foundation. Like the foundation's previous films Elephants Dream and Big Buck Bunny, the film was made using Blender, a free software application for animation created and supported by the same foundation. Sintel was produced by Ton Roosendaal, chairman of the Foundation, and directed by Colin Levy, an artist at Pixar Animation Studios. (code-named Durian)",R.drawable.sintel));
		torrents.add(new Torrent("Dracula","Video","2010-10-08",4321,56,6,TORRENT_HEALTH.YELLOW,"Dracula...",R.drawable.dracula));
		torrents.add(new Torrent("King Kong","Video","2010-07-23",12353,66,2,TORRENT_HEALTH.UNKNOWN,"King Kong...",R.drawable.kingkong));
		torrents.add(new Torrent("Tears of Steal","Video","2003-01-23",423.12,102,12,TORRENT_HEALTH.RED,"Tears of Steel...",R.drawable.tos));
		torrents.add(new Torrent("To The Last Man","Video","2000-03-28",1235.2,22,0,TORRENT_HEALTH.UNKNOWN,"To The Last Man...",R.drawable.lastman));
		torrents.add(new Torrent("Attack of the 50 ft woman","Video","2003-01-23",413.123,45,2,TORRENT_HEALTH.UNKNOWN,"Attack of the 50 ft woman...",R.drawable.fiftyft));
		torrents.add(new Torrent("Draculas Daughter","Video","2013-11-23",124,2,0,TORRENT_HEALTH.RED,"Draculas Daughter...",R.drawable.dracula_daughter));		
		torrents.add(new Torrent("Lusty Men","Video","2001-11-09",564.1,234,33,TORRENT_HEALTH.GREEN,"Lusty Men...",R.drawable.lustymen));
		torrents.add(new Torrent("Mantis","Video","1999-12-15",124.5,8,1,TORRENT_HEALTH.RED,"Mantis...",R.drawable.mantis));		
		torrents.add(new Torrent("Son of Frankenstein","Video","2003-12-18",342.5,21,3,TORRENT_HEALTH.YELLOW,"Son of Frankenstein...",R.drawable.sof));	
	}
	
	/** 
	 * @param id the position of the torrent in the list
	 * @return the torrent belonging to the id
	 */
	public static Torrent getTorrentById(int id)
	{
		return torrents.get(id);
	}
	
	/** 
	 * @return the number of torrents in the list
	 */
	public static int getNumberOfTorrents()
	{
		if(torrents != null)
			return torrents.size();
		return 0;
	}

	/**
	 * @param id the id of the torrent
	 * @return True iff the list contains a torrent at index=id
	 */
	public static boolean containsTorrentWithID(int id)
	{
		return (id>=0 && id<torrents.size() && torrents.get(id) != null);
	}
	
	public static ArrayList<ThumbItem> getThumbItems()
	{
		ArrayList<ThumbItem> gridArray = new ArrayList<ThumbItem>();
		for(Torrent torrent: torrents)
		{
			gridArray.add(toThumbItem(torrent));
		}
		return gridArray;
	}

	private static ThumbItem toThumbItem(Torrent torrent) {		
		int fz = (int) Math.round(torrent.getFilesize());
		return new ThumbItem(torrent.getName(), torrent.getThumbnailID(), torrent.getHealth(), fz);
	}
}