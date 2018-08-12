package com.example.inv.test.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inv.test.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ElementHolder> {
    private ArrayList<Element> elements;

    public Adapter(ArrayList<Element> elements){
        this.elements = elements;
    }

    @NonNull
    @Override
    public ElementHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View iv = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_layout, parent, false);
        return new ElementHolder(iv, i);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementHolder holder, int i) {
        Element e = elements.get(i);
        holder.title.setText("Card: " + i);
        holder.content.setText(e.getContent() + " " + holder.clicked);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public class ElementHolder extends RecyclerView.ViewHolder{
        public TextView title, content;
        public Button button;
        public Element element;

        public int clicked = 0;

        public ElementHolder(final View iv, int i){
            super(iv);
            title = (TextView) iv.findViewById(R.id.title);
            content = (TextView) iv.findViewById(R.id.content);
            button = (Button) iv.findViewById(R.id.button);

            element = elements.get(i);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked++;
                    Toast.makeText(iv.getContext(), "Clicked " + title.getText(), Toast.LENGTH_LONG).show();
                    content.setText(element.getContent() + " " + clicked);
                }
            });
        }


    }
}
