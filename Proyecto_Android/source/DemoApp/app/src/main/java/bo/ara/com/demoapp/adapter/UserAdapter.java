package bo.ara.com.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import bo.ara.com.demoapp.R;
import bo.ara.com.demoapp.model.Post;
import bo.ara.com.demoapp.model.User;

/**
 * Created by LENOVO on 15/06/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(Context context) {
        super(context, R.layout.user_row_layout);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Recupera objeto con la posicion
        User user =  getItem(position);

        View currentView;
        //convertview = null -> crear nueva vista
        //otherwise -> usar la vista ya creada
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            currentView = inflater.inflate(R.layout.user_row_layout, parent, false);
        }
        else{
            currentView = convertView;
        }

        //Buscamos referencia nuestros objetos dentro del layout
        TextView username = (TextView) currentView.findViewById(R.id.text_username);
        TextView email = (TextView) currentView.findViewById(R.id.text_email);

        //Llena los datos al layout
        username.setText(user.getUsername());
        email.setText(user.getEmail());

        //Devuelve vista procesada
        return currentView;
    }
}
