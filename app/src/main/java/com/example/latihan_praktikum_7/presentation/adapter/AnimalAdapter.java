package com.example.latihan_praktikum_7.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.latihan_praktikum_7.R;
import com.example.latihan_praktikum_7.data.database.DatabaseHelper;
import com.example.latihan_praktikum_7.data.entity.Animal;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private List<Animal> animalList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Animal animal);
    }

    public AnimalAdapter(List<Animal> animalList, OnItemClickListener listener, Context context) {
        this.animalList = animalList;
        this.listener = listener;
        this.context = context;
    }

    public void setAnimals(List<Animal> animals) {
        this.animalList = animals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.tvName.setText(animal.getName());
        holder.tvSpecies.setText("Species: " + animal.getSpecies());
        holder.tvDescription.setText("Description: " + animal.getDescription());

        // Cek status favorit saat menampilkan item
        if (animal.getIsFavorite() == 1) {
            holder.btnFavorite.setImageResource(R.drawable.ic_star_filled); // Ikon favorit terisi
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite); // Ikon favorit kosong
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(animal);
            }
        });

        holder.btnFavorite.setOnClickListener(v -> {
            boolean willBeFavorite = animal.getIsFavorite() != 1;

            // Update status favorit di database
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            dbHelper.setFavorite(animal.getId(), willBeFavorite);

            // Update data di objek
            animal.setIsFavorite(willBeFavorite ? 1 : 0);

            // Tampilkan notifikasi hanya jika menandai sebagai favorit
            if (willBeFavorite) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "favorite_channel")
                        .setSmallIcon(R.drawable.ic_favorite)
                        .setContentTitle("Hewan Ditambahkan")
                        .setContentText("Hewan favorit berhasil ditambahkan!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(1, builder.build());
            }

            // Perbarui UI
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvSpecies, tvDescription;
        ImageButton btnFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSpecies = itemView.findViewById(R.id.tv_species);
            tvDescription = itemView.findViewById(R.id.tv_description);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
