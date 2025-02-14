package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ResourceAsColor")
@Composable
internal fun FilterChipList(
    listOfMuscleSubGroup: List<MuscleSubGroupModel>,
    onItemClick: (item: MuscleSubGroupModel) -> Unit,
    enabled: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp)
            .background(colorResource(R.color.empty)),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listOfMuscleSubGroup) { item ->
            FilterChip(
                enabled = enabled,
                modifier = Modifier.height(22.dp),
                onClick = { onItemClick(item) },
                label = { Text(fontSize = 16.sp, text = item.name) },
                selected = false
            )
        }
    }
}

@Preview
@Composable
fun AssistChipListPreview() {
    val listOfMuscleSubGroup: MutableList<MuscleSubGroupModel> = mutableListOf()
    for (i in 1..5) {
        listOfMuscleSubGroup.add(
            MuscleSubGroupModel(id = i, name = "Grupo$i")
        )
    }
    FilterChipList(
        listOfMuscleSubGroup = listOfMuscleSubGroup,
        enabled = false,
        onItemClick = {}
    )
}