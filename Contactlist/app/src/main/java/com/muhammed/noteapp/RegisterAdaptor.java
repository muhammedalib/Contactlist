package com.muhammed.noteapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RegisterAdaptor extends RecyclerView.Adapter<RegisterAdaptor.RegisterHolder> {

    private Context context;
    private ArrayList<ExampleRegister>exampleRegisterArrayList;
    DataBaseHelper dataBaseHelper;

    public RegisterAdaptor(Context context, ArrayList<ExampleRegister> exampleRegisters) {
        this.context = context;
        this.exampleRegisterArrayList = exampleRegisters;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public RegisterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view,parent,false);
        return new RegisterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisterAdaptor.RegisterHolder holder, @SuppressLint("RecyclerView") int position) {
        ExampleRegister exampleRegister = exampleRegisterArrayList.get(position);
        String id = exampleRegister.getId();
        String name = exampleRegister.getName();
        String image = exampleRegister.getImage();
        String number = exampleRegister.getNumber();
        String birthday = exampleRegister.getBirthday();
        String mail = exampleRegister.getMail();
        String address = exampleRegister.getAddress();
        String upload = exampleRegister.getUpload();
        String upgrade = exampleRegister.getUpgrade();


        holder.TvName.setText(name);
        holder.TvNumber.setText(number);
        holder.TvMail.setText(mail);
        holder.TvBirthday.setText(birthday);
        if(image.equals("null"))
        {
            holder.ProfileImage.setImageResource(R.drawable.ic_baseline_person_24);
        }
        else
        {
            holder.ProfileImage.setImageURI(Uri.parse(image));
        }
        holder.BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Sil ve Güncelle", Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ValueDetail = new Intent(context,ValueDetailActivity.class);
                ValueDetail.putExtra("Value_ID",id);
                context.startActivity(ValueDetail);
            }
        });

        holder.BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDeleteWindow(
                        ""+position,
                        ""+id,
                        ""+name,
                        ""+number,
                        ""+address,
                        ""+mail,
                        ""+upgrade,
                        ""+upload,
                        ""+image,
                        ""+birthday);
            }
        });

    }

    private void UpdateDeleteWindow(String position,String id, String name, String number, String address, String mail, String upgrade, String update, String image, String birthday) {


        String [] Options = {"Update","Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int i) {
                if(i == 0 )
                {
                    Intent RegisterValueSend = new Intent(context,RegisterActivity.class);
                    RegisterValueSend.putExtra("ID",id);
                    RegisterValueSend.putExtra("NAME",name);
                    RegisterValueSend.putExtra("NUMBER",number);
                    RegisterValueSend.putExtra("ADDRESS",address);
                    RegisterValueSend.putExtra("BIRTHDAY",birthday);
                    RegisterValueSend.putExtra("IMAGE",image);
                    RegisterValueSend.putExtra("MAIL",mail);
                    RegisterValueSend.putExtra("UPGRADE",upgrade);
                    RegisterValueSend.putExtra("UPDATE",update);
                    RegisterValueSend.putExtra("DATE_SITUATION",true);
                    context.startActivity(RegisterValueSend);

                }
                if (i == 1)
                {
                    dataBaseHelper.DeleteValues(id);
                    Toast.makeText(context, id + "th Person Deleted From List.", Toast.LENGTH_SHORT).show();
                    ((MainActivity)context).onResume();
                }
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {

        return exampleRegisterArrayList.size();
    }

    public class RegisterHolder extends RecyclerView.ViewHolder {

        ImageView ProfileImage ;
        TextView TvName,TvNumber,TvBirthday,TvMail;
        ImageButton BtnUpdate;


        public RegisterHolder(@NonNull View itemView) {
            super(itemView);
            ProfileImage=itemView.findViewById(R.id.Profile_Image);
            TvName=itemView.findViewById(R.id.Rc_Name);
            TvNumber=itemView.findViewById(R.id.Rc_Number);
            TvBirthday=itemView.findViewById(R.id.Rc_Birthday);
            TvMail=itemView.findViewById(R.id.Rc_Mail);
            BtnUpdate=itemView.findViewById(R.id.btn_update);
        }
    }
}
