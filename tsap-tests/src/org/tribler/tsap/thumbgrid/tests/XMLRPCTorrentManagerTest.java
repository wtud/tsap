package org.tribler.tsap.thumbgrid.tests;

import java.util.ArrayList;
import java.util.HashMap;

import org.tribler.tsap.MainActivity;
import org.tribler.tsap.R;
import org.tribler.tsap.StatusViewer;
import org.tribler.tsap.Torrent;
import org.tribler.tsap.XMLRPC.tests.StubbedXMLRPCConnection;
import org.tribler.tsap.thumbgrid.ThumbAdapter;
import org.tribler.tsap.thumbgrid.XMLRPCTorrentManager;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class XMLRPCTorrentManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
	ThumbAdapter mAdapter;
	XMLRPCTorrentManager mManager;
	StatusViewer mStatusViewer;
	ProgressBar mProgressBar;
	TextView mTextView;
	StubbedXMLRPCConnection mConnection;
	ArrayList<Torrent> mTorrentList = new ArrayList<Torrent>();
	Torrent mTorrent = new Torrent("", "", 0l, 0, 0, "video");
	static final String SEARCH_STRING = "blabla";
	
	public XMLRPCTorrentManagerTest() {
		super(MainActivity.class);
		mConnection = StubbedXMLRPCConnection.getInstance();
		mConnection.setupStub();
		mTorrentList.add(mTorrent);
	}
	
	@Override
	protected void setUp() {
		mProgressBar = new ProgressBar(getActivity());
		mTextView = new TextView(getActivity());
		mStatusViewer = new StatusViewer(getActivity());
		mStatusViewer.updateViews(mProgressBar, mTextView);
		mAdapter = new ThumbAdapter(getActivity(), R.layout.thumb_grid_item);
		mManager = new XMLRPCTorrentManager(mConnection, mAdapter, mStatusViewer);
		mConnection.clearReturnValues();
	}
	
	public void waitForAsyncCalls() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			fail("Caught InterruptedException, which caused the test to fail.");
		}
	}
	
	public void testSearch() {
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_SEARCH_REMOTE, true);
		mAdapter.addNew(mTorrentList);
		mStatusViewer.disable();
		mManager.search(SEARCH_STRING);
		
		waitForAsyncCalls();
		
		assertTrue("Adapter is not empty after searching.", mAdapter.isEmpty());
		assertTrue("StatusViewer is not enabled", mStatusViewer.isEnabled());
		assertEquals("StatusViewer has incorrect message", getActivity().getResources().getText(R.string.thumb_grid_search_submitted), mTextView.getText());
	}
	public void testemptyPoll() {
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS_COUNT, 0);
		mManager.onPoll();
		
		assertEquals("Remote results retrieved, even though the count was 0.", 0, mConnection.getTimesCalled(XMLRPCTorrentManager.RPC_REMOTE_RESULTS));
	}
	public void testEqualPoll() {
		mAdapter.addNew(mTorrentList);
		
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS_COUNT, 1);
		mManager.onPoll();
		assertEquals("Remote results retrieved, even though the count was equal.", 0, mConnection.getTimesCalled(XMLRPCTorrentManager.RPC_REMOTE_RESULTS));
	}
	HashMap<String, Object> hashMapFromTorrent(Torrent tor) {
		HashMap<String, Object> torrentMap = new HashMap<String, Object>();
		torrentMap.put("num_seeders", tor.getSeeders());
		torrentMap.put("num_leechers", tor.getLeechers());
		torrentMap.put("length", ""+tor.getSize());
		torrentMap.put("infohash", tor.getInfoHash());
		torrentMap.put("name", tor.getName());
		torrentMap.put("category", tor.getCategory());
		return torrentMap;
	}
	
	@UiThreadTest
	public void testRetrieveOnPoll() {		
		Object[] list = {hashMapFromTorrent(mTorrent)};
		
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS, list);
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS_COUNT, 1);
		mManager.onPoll();
		
		assertEquals(1, mConnection.getTimesCalled(XMLRPCTorrentManager.RPC_REMOTE_RESULTS));
		assertEquals(1, mAdapter.getCount());
		assertEquals(mTorrent, mAdapter.getItem(0));
		assertEquals(false, mStatusViewer.isEnabled());
	}
	
	@UiThreadTest
	public void testFilteredOnPoll() {
		HashMap<String, Object> torMap = hashMapFromTorrent(mTorrent);
		torMap.put("category", "blabla");
		Object[] list = {torMap};
		
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS, list);
		mConnection.addReturnValue(XMLRPCTorrentManager.RPC_REMOTE_RESULTS_COUNT, 1);
		
		mManager.onPoll();
		
		assertEquals(1, mConnection.getTimesCalled(XMLRPCTorrentManager.RPC_REMOTE_RESULTS));
		assertEquals(0, mAdapter.getCount());
		assertEquals(true, mStatusViewer.isEnabled());
	}
}