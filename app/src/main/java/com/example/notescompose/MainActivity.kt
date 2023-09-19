package com.example.notescompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.material.TopAppBar
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notescompose.datastore.StoreNotes
import com.example.notescompose.ui.theme.BLACK
import com.example.notescompose.ui.theme.GOLD
import com.example.notescompose.ui.theme.WHITE
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NotesComponents()
            //passar minha fun que criei abaixo aqui no bloco principal para poder rendenrizar
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesComponents() {

    //salvar a anotacao
    val context = LocalContext.current
    //para crirar a coroutines
    val scope = rememberCoroutineScope()

    val storeNotes = StoreNotes(context)

    //para recuperar a anaotacao salva
    val noteSaved = storeNotes.getNotesTyped.collectAsState(initial = "")


    var notes by remember {
        mutableStateOf("")
    }
    //depois que criei a val noteSaved a minha notes vai receber minha notedSaved
    notes = noteSaved.value

    androidx.compose.material.Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = GOLD
            ) {
                Text(text = "Notes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = BLACK)

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                  //para salvar anotacao (nao esqucer de criar a val no inicio para receber remeberCoroutinesScope
                    scope.launch {
                        storeNotes.saveNotes(notes)
                        Toast.makeText(context, "note saved successfully", Toast.LENGTH_SHORT).show()
                    }
                },
                backgroundColor = GOLD,
                elevation = FloatingActionButtonDefaults.elevation(
                    8.dp
                )
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_save_24),
                    contentDescription = "Save Icon"
                )
            }
        }
    ) {
        //texto
        Column() {
            TextField(
                //var criada na fun
                value = notes,
                onValueChange = {
                    notes = it
                },
                label = {
                    Text(text = "Type here...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = WHITE,
                    cursorColor = GOLD,
                    focusedLabelColor = WHITE
                )
            )
        }
    }
}




