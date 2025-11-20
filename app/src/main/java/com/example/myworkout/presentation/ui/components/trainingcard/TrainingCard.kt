package com.example.myworkout.presentation.ui.components.trainingcard

import android.annotation.SuppressLint
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myworkout.Constants
import com.example.myworkout.Constants.Companion.DEFAULT_PADDING
import com.example.myworkout.Constants.Companion.SUB_GROUP_SECTION_BACKGROUND
import com.example.myworkout.Constants.Companion.TRAINING_CARD_PADDING_BOTTOM
import com.example.myworkout.Constants.Companion.TRAINING_NAME_MAX_HEIGHT
import com.example.myworkout.Constants.Companion.TRAINING_NAME_SHOULDER
import com.example.myworkout.R
import com.example.myworkout.domain.model.SubGroupModel
import com.example.myworkout.domain.model.TrainingModel
import com.example.myworkout.enums.DayOfWeek
import com.example.myworkout.enums.Status
import com.example.myworkout.extensions.setBackGroundColor
import com.example.myworkout.extensions.toDayOfWeek
import com.example.myworkout.extensions.toPortugueseString
import com.example.myworkout.extensions.trainingCardFilterChipListModifier
import com.example.myworkout.presentation.ui.components.commons.AlertDialog
import com.example.myworkout.presentation.ui.components.commons.CheckBox
import com.example.myworkout.presentation.ui.components.commons.CustomDialog
import com.example.myworkout.presentation.ui.components.commons.DropdownItem
import com.example.myworkout.presentation.ui.components.commons.IconButton
import com.example.myworkout.presentation.ui.components.commons.TextFieldComponent
import com.example.myworkout.presentation.viewmodel.TrainingSubGroupState
import com.example.myworkout.utils.Utils

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(35)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    filterChipListModifier: Modifier = Modifier,
    training: TrainingModel,
    trainingSubGroups: List<TrainingSubGroupState>,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    onUpdateTraining: (training: TrainingModel) -> Unit,
    onUpdateTrainingName: (value: String) -> Unit,
    onDeleteTraining: (training: TrainingModel) -> Unit,
    onUpdateSubGroup: (subGroup: SubGroupModel) -> Unit
) {
    // Training
    var isTrainingChecked by remember { mutableStateOf(training.status == Status.ACHIEVED) }
    var status by remember { mutableStateOf(training.status) }
    val trainingName = training.trainingName
    var dayOfWeek by remember { mutableStateOf(training.dayOfWeek.toPortugueseString()) }

    // Dialog
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf(R.string.dialog_title) }
    var dialogText by remember { mutableStateOf(R.string.dialog_text) }

    // Custom Dialog
    val focusRequester = remember { FocusRequester() }
    var trainingNameInternal by remember { mutableStateOf(training.trainingName) }
    var trainingToBeDeleted by remember { mutableStateOf(training) }

    // SubGroupSection
    var isEnabled by remember { mutableStateOf(training.status == Status.PENDING) }

    LaunchedEffect(training.status) {
        isEnabled = training.status == Status.PENDING
    }

    LaunchedEffect(training.trainingName) {
        trainingNameInternal = training.trainingName
    }

    LaunchedEffect(training.dayOfWeek) {
        dayOfWeek = training.dayOfWeek.toPortugueseString()
    }

    fun setInitialStates() {
        showDialog = false
        showCustomDialog = false
        showDeleteDialog = false
        status = training.status
        isTrainingChecked = training.isChecked
        onUpdateTrainingName(training.trainingName)
        trainingNameInternal = trainingName
    }

    StatusAlertDialogSection(
        dialogTitle = dialogTitle,
        dialogText = dialogText,
        showDialog = showDialog,
        onDismiss = { setInitialStates() },
        onConfirmation = {
            onUpdateTraining(
                TrainingModel(
                    trainingId = training.trainingId,
                    status = status,
                    trainingName = training.trainingName,
                    dayOfWeek = training.dayOfWeek,
                    isChecked = isTrainingChecked
                )
            )
            setInitialStates()
        }
    )

    DeleteAlertDialogSection(
        dialogTitle = dialogTitle,
        dialogText = dialogText,
        showDialog = showDeleteDialog,
        onDismiss = { setInitialStates() },
        onConfirmation = {
            onDeleteTraining(trainingToBeDeleted)
            setInitialStates()
        }
    )

    CustomDialogSection(
        showCustomDialog = showCustomDialog,
        trainingName = trainingNameInternal,
        title = dialogTitle,
        trainingStatus = training.status,
        focusRequester = focusRequester,
        listOfDays = listOfDays,
        dayOfWeek = dayOfWeek,
        onChangeTrainingName = {
            onUpdateTrainingName(it)
            trainingNameInternal = it
        },
        onChangeDayOfWeek = { dayOfWeek = it },
        onDismiss = {
            setInitialStates()
            dayOfWeek = training.dayOfWeek.toPortugueseString()
        },
        onConfirmation = {
            showCustomDialog = false
            onUpdateTraining(
                TrainingModel(
                    trainingId = training.trainingId,
                    status = training.status,
                    trainingName = trainingNameInternal,
                    dayOfWeek = dayOfWeek.toDayOfWeek(),
                    isChecked = training.isChecked
                )
            )
        }
    )

    Card(
        modifier = modifier
            .padding(bottom = TRAINING_CARD_PADDING_BOTTOM)
            .combinedClickable(
                onClick = {
                    showCustomDialog = false
                },
                onLongClick = {
                    dialogTitle = R.string.edit_training
                    showCustomDialog = true
                },
                onDoubleClick = {
                    trainingToBeDeleted = training
                    dialogTitle = R.string.delete_training
                    dialogText = R.string.no_turning_back
                    showDeleteDialog = true
                }
            ),
        colors = Utils().buttonSectionCardsColors(),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.cardElevation(),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            SetTrainingName(
                trainingName = training.trainingName,
                status = training.status
            )
            SetSubGroupSection(
                filterChipListModifier = filterChipListModifier,
                training = training,
                isEnabled = isEnabled,
                trainingSubGroups = trainingSubGroups,
                onUpdateSubGroup = { onUpdateSubGroup(it) },
            )
            SetCheckboxSection(
                training = training,
                onCheckTraining = {
                    showCustomDialog = false
                    isTrainingChecked = it
                },
                onChangeStatus = { status = it },
                onChangeDialogText = { dialogText = it },
                onChangeDialogTitle = { dialogTitle = it },
                onShowDialog = {
                    showCustomDialog = false
                    showDialog = it
                }
            )
        }
    }
}

@Composable
private fun CustomDialogSection(
    title: Int,
    trainingName: String,
    trainingStatus: Status,
    showCustomDialog: Boolean,
    focusRequester: FocusRequester,
    listOfDays: List<Pair<DayOfWeek, Boolean>>,
    dayOfWeek: String,
    onChangeTrainingName: (name: String) -> Unit,
    onChangeDayOfWeek: (dayOfWeek: String) -> Unit,
    onConfirmation: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (showCustomDialog && trainingStatus == Status.PENDING) {
        CustomDialog(
            modifier = Modifier.padding(16.dp),
            title = title,
            message = null,
            onConfirmation = {
                onConfirmation()
            },
            onDismissRequest = { onDismiss() },
            content = {
                TextFieldComponent(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = trainingName,
                    isSingleLine = true,
                    focusRequester = focusRequester,
                    onValueChange = { onChangeTrainingName(it) },
                    enabled = true,
                    label = {
                        Text(
                            text = stringResource(R.string.training_name_string),
                            color = colorResource(R.color.title_color),
                            fontSize = 16.sp
                        )
                    }
                )

                DropdownItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    items = listOfDays,
                    text = dayOfWeek,
                    enabled = true,
                    onItemClick = {
                        onChangeDayOfWeek(it)
                    }
                )
            }
        )
    }
}

@Composable
private fun SetCheckboxSection(
    training: TrainingModel,
    onCheckTraining: (value: Boolean) -> Unit,
    onChangeStatus: (status: Status) -> Unit,
    onChangeDialogTitle: (value: Int) -> Unit,
    onChangeDialogText: (value: Int) -> Unit,
    onShowDialog: (value: Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ) {
        CheckboxSection(
            training = training,
            onCheckTraining = { onCheckTraining(it) },
            onChangeStatus = { onChangeStatus(it) },
            onChangeDialogTitle = { onChangeDialogTitle(it) },
            onChangeDialogText = { onChangeDialogText(it) },
            onShowDialog = { onShowDialog(it) },
        )
        SkipButtonSection(
            training = training,
            onChangeDialogTitle = { onChangeDialogTitle(it) },
            onChangeDialogText = { onChangeDialogText(it) },
            onChangeStatus = { onChangeStatus(it) },
            onShowDialog = { onShowDialog(it) }
        )
    }
}

@Composable
private fun SkipButtonSection(
    training: TrainingModel,
    onChangeDialogTitle: (Int) -> Unit,
    onChangeDialogText: (Int) -> Unit,
    onChangeStatus: (Status) -> Unit,
    onShowDialog: (Boolean) -> Unit
) {
    if (training.status == Status.PENDING || training.status == Status.MISSED)
        Button(
            modifier = Modifier
                .padding(start = 8.dp, bottom = 8.dp)
                .height(30.dp),
            onClick = {
                onChangeDialogTitle(if (training.status == Status.PENDING) R.string.dialog_title_skip else R.string.dialog_title_restore)
                onChangeDialogText(R.string.dialog_text)
                onChangeStatus(if (training.status != Status.MISSED) Status.MISSED else Status.PENDING)
                onShowDialog(true)
            },
            enabled = true,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_skip_training))
        ) {
            Text(
                fontSize = 10.sp,
                text = stringResource(
                    if (training.status == Status.PENDING) R.string.skip_training
                    else R.string.restore_training
                )
            )
        }
}

@Composable
private fun CheckboxSection(
    training: TrainingModel,
    onCheckTraining: (Boolean) -> Unit,
    onChangeStatus: (Status) -> Unit,
    onChangeDialogTitle: (Int) -> Unit,
    onChangeDialogText: (Int) -> Unit,
    onShowDialog: (Boolean) -> Unit
) {
    if (training.status != Status.MISSED) {
        CheckBox(
            status = training.status,
            isTrainingChecked = training.isChecked,
            enabled = true,
            onChecked = {
                if (training.isChecked) {
                    onCheckTraining(false)
                    onChangeStatus(Status.PENDING)
                    onChangeDialogTitle(R.string.dialog_title_restore)
                    onChangeDialogText(R.string.dialog_text_restore)
                } else {
                    onCheckTraining(true)
                    onChangeStatus(Status.ACHIEVED)
                    onChangeDialogTitle(R.string.dialog_title)
                    onChangeDialogText(R.string.dialog_text)
                }
                onShowDialog(true)
            },
        )
    }
}

@Composable
private fun StatusAlertDialogSection(
    dialogTitle: Int,
    dialogText: Int,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            confirmButtonText = stringResource(R.string.dialog_confirm_text),
            cancelButtonText = stringResource(R.string.dialog_cancel_text),
            onDismissRequest = { onDismiss() },
            onConfirmation = { onConfirmation() },
            dialogTitle = stringResource(dialogTitle),
            dialogText = stringResource(dialogText),
        )
    }
}

@Composable
private fun DeleteAlertDialogSection(
    dialogTitle: Int,
    dialogText: Int,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            confirmButtonText = stringResource(R.string.dialog_confirm_text),
            cancelButtonText = stringResource(R.string.dialog_cancel_text),
            onDismissRequest = { onDismiss() },
            onConfirmation = { onConfirmation() },
            dialogTitle = stringResource(dialogTitle),
            dialogText = stringResource(dialogText),
        )
    }
}

@Composable
private fun SetTrainingName(
    trainingName: String,
    status: Status
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(color = colorResource(status.setBackGroundColor()))
            .height(TRAINING_NAME_MAX_HEIGHT)
            .fillMaxWidth(),
    ) {
        if (status != Status.EMPTY) Text(trainingName)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SetSubGroupSection(
    filterChipListModifier: Modifier,
    training: TrainingModel,
    isEnabled: Boolean,
    trainingSubGroups: List<TrainingSubGroupState>,
    onUpdateSubGroup: (subGroup: SubGroupModel) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = colorResource(SUB_GROUP_SECTION_BACKGROUND))
            .fillMaxWidth()
    ) {
        if (training.status != Status.EMPTY) {
            FilterChipList(
                modifier = filterChipListModifier.trainingCardFilterChipListModifier(),
                backGroundColor = SUB_GROUP_SECTION_BACKGROUND,
                orientation = HomeGrid,
                orientationProps = HomeGridProps(
                    colors = Utils().selectableChipColors(),
                    listOfMuscleSubGroup = trainingSubGroups,
                    enabled = isEnabled,
                    horizontalSpacedBy = DEFAULT_PADDING,
                    verticalSpacedBy = DEFAULT_PADDING,
                    onItemClick = { onUpdateSubGroup(it) }
                ),
            )
        } else IconButton(painter = painterResource(R.drawable.add_icon))
    }
}

//@RequiresApi(35)
//@Preview
//@Composable
//fun TrainingCardPreview() {
//    val constants = Constants()
//    val shoulder = TRAINING_NAME_SHOULDER
//    Column {
//        Status.values().forEach {
//            TrainingCard(
//                modifier = Modifier
//                    .padding(bottom = 4.dp)
//                    .width(200.dp),
//                training = constants.getTrainingMock(it, shoulder, DayOfWeek.MONDAY),
//                subGroups = constants.newSubGroupsMock,
//                filterChipListModifier = Modifier,
//                listOfDays = Constants().getListOfDays(),
//                onUpdateTraining = {},
//                onUpdateTrainingName = {},
//                onDeleteTraining = {},
//                onUpdateSubGroup = {}
//            )
//        }
//    }
//}