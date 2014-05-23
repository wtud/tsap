package org.tribler.tsap;

import org.tribler.tsap.videoInfoScreen.Torrent;
import org.tribler.tsap.videoInfoScreen.TorrentManager;
import org.videolan.vlc.gui.video.VideoPlayerActivity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Fragment that shows the detailed info belonging to the selected torrent in
 * the thumb grid
 * 
 * @author Niels Spruit
 */
public class VideoInfoFragment extends Fragment {

	private int torrentID = 0;
	private View view;
	private Context context;
	private View.OnClickListener mViewButtonOnClickListener;
	private TorrentManager mTorrentManager;

	/**
	 * Initializes the video info fragment's layout according to the selected
	 * torrent
	 * 
	 * @param inflater
	 *            The inflater used to inflate the video info layout
	 * @param container
	 *            The container view of this fragment
	 * @param savedInstanceState
	 *            The state of the saved instance
	 * @return The created view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_video_info, container, false);
		context = container.getContext();
		if (getArguments() != null)
			torrentID = getArguments().getInt("torrentID", 0);

		mTorrentManager = TorrentManager.getInstance();
		setValues();
		return view;
	}

	/**
	 * Changes the values of the views according to the torrent metadata
	 */
	private void setValues() {
		Torrent selectedTorrent = getCurrentTorrent();
		TextView title = (TextView) view.findViewById(R.id.video_info_title);
		title.setText(selectedTorrent.getName());

		TextView type = (TextView) view.findViewById(R.id.video_details_type);
		type.setText(selectedTorrent.getType());

		TextView date = (TextView) view
				.findViewById(R.id.video_details_upload_date);
		date.setText(selectedTorrent.getUploadDate());

		TextView size = (TextView) view
				.findViewById(R.id.video_details_filesize);
		size.setText(Double.toString(selectedTorrent.getFilesize()));

		TextView seeders = (TextView) view
				.findViewById(R.id.video_details_seeders);
		seeders.setText(Integer.toString(selectedTorrent.getSeeders()));

		TextView leechers = (TextView) view
				.findViewById(R.id.video_details_leechers);
		leechers.setText(Integer.toString(selectedTorrent.getLeechers()));

		TextView descr = (TextView) view
				.findViewById(R.id.video_info_description);
		descr.setText(selectedTorrent.getDescription());

		ImageView thumb = (ImageView) view
				.findViewById(R.id.video_info_thumbnail);
		loadBitmap(selectedTorrent.getThumbnailID(), thumb);
		setViewButtonListener();
	}

	/**
	 * Sets the listener of the 'Play video' button to a listener that starts
	 * VLC when the button is pressed
	 */
	private void setViewButtonListener() {
		Button viewButton = (Button) view
				.findViewById(R.id.video_info_stream_video);
		mViewButtonOnClickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String URL = "http://inventos.ru/video/sintel/sintel-q3.mp4";
				Uri link = Uri.parse(URL);// Uri.fromFile(f);
				Intent intent = new Intent(Intent.ACTION_VIEW, link,
						getActivity().getApplicationContext(),
						VideoPlayerActivity.class);
				startActivity(intent);
			}
		};
		viewButton.setOnClickListener(mViewButtonOnClickListener);
	}

	/**
	 * Returns the selected torrent
	 * 
	 * @return The torrent with id=torrentID iff torrent with id=torrentID
	 *         exists, otherwise torrent with id=0 is returned
	 */
	private Torrent getCurrentTorrent() {
		if (mTorrentManager.containsTorrentWithID(torrentID))
			return mTorrentManager.getTorrentById(torrentID);
		return mTorrentManager.getTorrentById(0);
	}

	/**
	 * Loads the thumbnail of the selected torrent
	 * 
	 * @param resId
	 *            The resource id of the thumbnail
	 * @param mImageView
	 *            The ImageView in which the thumbnail should be loaded
	 */
	private void loadBitmap(int resId, ImageView mImageView) {
		float dens = context.getResources().getDisplayMetrics().density;
		int thumbWidth = (int) (100 * dens);
		int thumbHeight = (int) (150 * dens);
		Picasso.with(context).load(resId).placeholder(R.drawable.default_thumb)
				.resize(thumbWidth, thumbHeight).into(mImageView);
	}

}
