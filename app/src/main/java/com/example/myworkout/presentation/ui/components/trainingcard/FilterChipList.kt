package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myworkout.R
import com.example.myworkout.domain.model.MuscleSubGroupModel
import com.example.myworkout.utils.DEFAULT_PADDING
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("ResourceAsColor")
@Composable
internal fun FilterChipList(
    modifier: Modifier = Modifier,
    backGroundColor: Int,
    orientation: Orientation,
    orientationProps: OrientationProps,
) {
    val modifier = modifier.background(colorResource(backGroundColor))
    orientation.Render(
        modifier = modifier,
        props = orientationProps
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun AssistChipListPreview() {
    val listOfMuscleSubGroup: MutableList<MuscleSubGroupModel> = mutableListOf()
    for (i in 1..5) {
        listOfMuscleSubGroup.add(
            MuscleSubGroupModel(id = i, name = "Grupo$i")
        )
    }
    Column(
        modifier = Modifier.padding(DEFAULT_PADDING)
    ) {
        FilterChipList(
            backGroundColor = R.color.white,
            orientation = Vertical,
            orientationProps = VerticalProps(
                colors = Utils().selectableChipColors(),
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                enabled = false,
                verticalSpacedBy = 0.dp,
                onItemClick = {}
            ),
        )

        FilterChipList(
            backGroundColor = R.color.white,
            orientation = Horizontal,
            orientationProps = HorizontalProps(
                colors = Utils().selectableChipColors(),
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                enabled = false,
                horizontalSpacedBy = 4.dp,
                onItemClick = {}
            ),
        )

        FilterChipList(
            modifier = Modifier.width(150.dp),
            backGroundColor = R.color.white,
            orientation = Grid,
            orientationProps = GridProps(
                colors = Utils().selectableChipColors(),
                listOfMuscleSubGroup = listOfMuscleSubGroup,
                enabled = false,
                horizontalSpacedBy = 2.dp,
                verticalSpacedBy = 0.dp,
                onItemClick = { }
            ),
        )
    }
}