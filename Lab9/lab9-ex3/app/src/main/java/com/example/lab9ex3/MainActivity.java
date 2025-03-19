package com.example.lab9ex3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private char[][] tablajoc = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };
    private boolean randulLuiX=true;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        status=findViewById(R.id.status);
        status.setText("Randul lui X");
    }

    public void Clicked(View view) {
        Button button = (Button) view;
        String buttonId = button.getResources().getResourceEntryName(button.getId());
        int rand = Character.getNumericValue(buttonId.charAt(6));
        int coloana = Character.getNumericValue(buttonId.charAt(7));

        if (tablajoc[rand][coloana] == ' ') {
            if (randulLuiX) {
                tablajoc[rand][coloana] = 'X';
                button.setText("X");
            } else {
                tablajoc[rand][coloana] = 'O';
                button.setText("O");
            }

            if (castigator()) {
                if (randulLuiX) {
                    status.setText("Jucatorul X a castigat!");
                } else {
                    status.setText("Jucatorul O a castigat!");
                }
                return;
            }

            boolean remiza = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablajoc[i][j] == ' ') {
                        remiza = false;
                        break;
                    }
                }
            }

            if (remiza) {
                status.setText("Egalitate!");
                return;
            }

            if (randulLuiX) {
                randulLuiX = false;
                status.setText("Randul lui O");
            } else {
                randulLuiX = true;
                status.setText("Randul lui X");
            }
        }else{
            Toast.makeText(this, "Spatiul este deja ocupat!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean castigator(){
        for(int i=0;i<3;i++){
            if(tablajoc[i][0]!= ' ' && tablajoc[i][0]==tablajoc[i][1] && tablajoc[i][0]==tablajoc[i][2])
                return true;
        }

        for(int i=0;i<3;i++)
        {
            if(tablajoc[0][i]!= ' ' && tablajoc[0][i]==tablajoc[1][i] && tablajoc[0][i]==tablajoc[2][i])
                return true;
        }

        if(tablajoc[0][0]!=' ' && tablajoc[0][0]==tablajoc[1][1] && tablajoc[0][0]==tablajoc[2][2])
            return true;

        if(tablajoc[0][2]!= ' ' && tablajoc[0][2]==tablajoc[1][1] && tablajoc[0][2]==tablajoc[2][0])
            return true;

        return false;
    }

    public void resetGame(View view){
        randulLuiX=true;
        status.setText("Randul lui X");
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                tablajoc[i][j]=' ';
                String buttonId="button"+i+j;
                int ID=getResources().getIdentifier(buttonId, "id", getPackageName());
                Button button=findViewById(ID);
                button.setText("");
                button.setEnabled(true);
            }
    }

}