package com.example.sqlitedatabse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ImageView ivDelete,ivEdit;

    private List<User> userList;
    private Activity context;
    private RoomDb database;

    public Adapter(Activity context,List<User> userList){
        this.context = context;
        this.userList = userList;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        User user = userList.get(position);

        database = RoomDb.getInstance(context);

        holder.name.setText("Name : " + user.getFirstName());
        holder.lastname.setText("Lastname :" + user.getLastName());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = userList.get(holder.getAdapterPosition());

                database.userDao().delete(user);

                int position = holder.getAdapterPosition();
                userList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,userList.size());


            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = userList.get(holder.getAdapterPosition());
                final int sID = user.getUid();

                String sName = user.getFirstName();
                String sLastname = user.getLastName();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int with = WindowManager.LayoutParams.MATCH_PARENT;

                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(with,height);

                dialog.show();

                final EditText etFirstname = dialog.findViewById(R.id.edit_Text);
                final EditText etLastname = dialog.findViewById(R.id.etLastname);
                Button btnUpdate = dialog.findViewById(R.id.btn_update);

                etFirstname.setText(sName);
                etLastname.setText(sLastname);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        String uName = etFirstname.getText().toString();
                        String uLastname = etLastname.getText().toString();

                        database.userDao().update(sID,uName,uLastname);
                        userList.clear();
                        userList.addAll(database.userDao().getAll());
                        notifyDataSetChanged();
                    }
                });




            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,lastname;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            lastname = itemView.findViewById(R.id.tv_lastname);

            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }


}
