package org.tribler.tsap.thumbgrid.tests;

import org.tribler.tsap.thumbgrid.TORRENT_HEALTH;

import android.graphics.Color;
import junit.framework.TestCase;

public class TorrentHealthTest extends TestCase {
	public void testTorrentHealth () {
		assertEquals(Color.GREEN, TORRENT_HEALTH.toColor(TORRENT_HEALTH.GREEN));
		assertEquals(Color.YELLOW, TORRENT_HEALTH.toColor(TORRENT_HEALTH.YELLOW));
		assertEquals(Color.RED, TORRENT_HEALTH.toColor(TORRENT_HEALTH.RED));
		assertEquals(Color.GRAY, TORRENT_HEALTH.toColor(TORRENT_HEALTH.UNKNOWN));
	}
}