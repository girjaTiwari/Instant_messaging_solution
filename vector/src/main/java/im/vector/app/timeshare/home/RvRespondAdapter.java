package im.vector.app.timeshare.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import im.vector.app.R;
import im.vector.app.timeshare.ApiClass;
import im.vector.app.timeshare.home.model.Respond;

public class RvRespondAdapter extends RecyclerView.Adapter<RvRespondAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<Respond>arrayList=new ArrayList<>();
    String timeZone="";

    public RvRespondAdapter(Context mContext, ArrayList<Respond> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rvitem_respond,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Respond model = arrayList.get(position);
        holder.tv_name.setText(model.getUser_name());
        holder.tv_message.setText(model.getRespond());
        holder.tv_respondtime.setText(model.getEpochTime());

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat crunchifyFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
        String currentTime = crunchifyFormat.format(today);
        System.out.println("Current Time:>>"+currentTime);
        try {
            Date date = crunchifyFormat.parse(currentTime);
            long epochTime = date.getTime();
            System.out.println("Current Time in Epoch:>>"+epochTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        Glide.with(mContext)
                .load(ApiClass.IMAGE_BASE_URL+model.getUser_pic())
                .placeholder(R.drawable.avtar)
                .into(holder.iv_avtar);

        Date currentTim = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = df.format(currentTim);
        System.out.println("cuurent>>"+currentTim);

        try {
            String strTime = model.getCreated_at();
            if (strTime!=null && !strTime.equals("")){
                String str = removeLat8Char(strTime);
                String[] separated = str.split("T");
                String date = separated[0];
                String time = separated[1];
                String[] t= time.split("Z");
                String tspit = t[0];
            //    holder.tv_respondtime.setText(date+" "+tspit);
                String apiDate = date+" "+tspit;

                //start date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = format.parse(apiDate);
                Date startDateLocal=startDate;

              //  System.out.println("start>>"+startDateLocal);

                //start date
                SimpleDateFormat current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date currentDate = current.parse(formattedDate);
                Date currentDateLocal=currentDate;
                System.out.println("current>>"+currentDateLocal);

                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long different = currentDateLocal.getTime() - startDateLocal.getTime();

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;

              //  System.out.println("diff>>"+elapsedDays+"-"+elapsedHours+"-"+elapsedMinutes+"-"+elapsedSeconds);

              /*  if (elapsedDays>0){
                    holder.tv_respondtime.setText(""+elapsedHours+"  day ago");
                }
                if (elapsedHours<24){
                    holder.tv_respondtime.setText(""+elapsedHours+"  hours ago");
                }
                if (elapsedDays==0 && elapsedDays==0){
                    holder.tv_respondtime.setText(""+elapsedMinutes+"  minutes ago");
                }*/

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String removeLat8Char(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 8);
        }
        return str;
    }

    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_avtar;
        TextView tv_name,tv_message,tv_respondtime,tv_reply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avtar = itemView.findViewById(R.id.iv_avtar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_respondtime = itemView.findViewById(R.id.tv_respondtime);
           // tv_reply = itemView.findViewById(R.id.tv_reply);
        }
    }
}
