package com.example.cryptotracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NotesRvAdaapter extends RecyclerView.Adapter<NotesRvAdaapter.NoteViewHolder> {
    private final Context context;
    private final ItemClicked listener;
    private final ArrayList<Note> allNotes = new ArrayList<>();
    private final String imageUrl = "https://res.cloudinary.com/dxi90ksom/image/upload/";

    public NotesRvAdaapter(Context context, ItemClicked listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        final NoteViewHolder viewHolder = new NoteViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(allNotes.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note currentNote = allNotes.get(position);
        USD data = currentNote.getMeanings().get(position).getQuote().getUSD();
         Data json = currentNote.getMeanings().get(position);

        NumberFormat str = NumberFormat.getCurrencyInstance();
        NumberFormat strp = NumberFormat.getInstance();
        strp.setMaximumFractionDigits(2);

        holder.name.setText(json.getName());
        holder.symbol.setText(json.getSymbol());

        Glide.with(holder.itemView.getContext())
                .load(imageUrl + json.getSymbol().toLowerCase() + ".png")
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.usdc1)
                .into(holder.image);

        double a = data.getPercentChange24h();
        holder.changeph.setTextColor(
                Color.parseColor(
                        a < 0 ? "#ff0000" : "#32CD32"
                )
        );

        holder.price.setText(str.format(data.getPrice()));
        holder.changeph.setText(String.format("%s%%", strp.format(a)));
    }

    @Override
    public int getItemCount() {
        return allNotes.size();
    }

    public void updateList(List<Note> newList) {
        allNotes.clear();
        allNotes.addAll(newList);
        notifyDataSetChanged();
    }

    public interface ItemClicked {
        void onItemClicked(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView name, symbol, price, changeph;
        ImageView image;

        public NoteViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            symbol = itemView.findViewById(R.id.symbol);
            price = itemView.findViewById(R.id.price);
            changeph = itemView.findViewById(R.id.changeph);
            image = itemView.findViewById(R.id.image);
        }
    }
}
