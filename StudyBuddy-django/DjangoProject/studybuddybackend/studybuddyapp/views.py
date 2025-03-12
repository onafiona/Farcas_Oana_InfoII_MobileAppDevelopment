from django.shortcuts import render, get_object_or_404, redirect
from .models import Materie, Tema, Proiect, Examen
from .forms import TemaForm, ProiectForm, ExamenForm


# Vizualizarea pentru lista de materii
def materii_list(request):
    materii = Materie.objects.all()
    return render(request, 'studybuddyapp/materii_list.html', {'materii': materii})

# Vizualizarea pentru lista de teme
def teme_list(request):
    teme = Tema.objects.all()
    return render(request, 'studybuddyapp/teme_list.html', {'teme': teme})

# Vizualizarea pentru lista de proiecte
def proiecte_list(request):
    proiecte = Proiect.objects.all()
    return render(request, 'studybuddyapp/proiecte_list.html', {'proiecte': proiecte})

# Vizualizarea pentru lista de examene
def examene_list(request):
    examene = Examen.objects.all()
    return render(request, 'studybuddyapp/examene_list.html', {'examene': examene})

def adauga_tema(request):
    if request.method == 'POST':
        form = TemaForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('teme_list')
    else:
        form = TemaForm()
    return render(request, 'studybuddyapp/adauga_tema.html', {'form': form})

# Adăugarea unui proiect nou
def adauga_proiect(request):
    if request.method == 'POST':
        form = ProiectForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('proiecte_list')
    else:
        form = ProiectForm()
    return render(request, 'studybuddyapp/adauga_proiect.html', {'form': form})

# Adăugarea unui examen nou
def adauga_examen(request):
    if request.method == 'POST':
        form = ExamenForm(request.POST)
        if form.is_valid():
            form.save()
            return redirect('examene_list')
    else:
        form = ExamenForm()
    return render(request, 'studybuddyapp/adauga_examen.html', {'form': form})

# Editarea unei teme
def editeaza_tema(request, pk):
    tema = get_object_or_404(Tema, pk=pk)
    if request.method == 'POST':
        form = TemaForm(request.POST, instance=tema)
        if form.is_valid():
            form.save()
            return redirect('teme_list')
    else:
        form = TemaForm(instance=tema)
    return render(request, 'studybuddyapp/editeaza_tema.html', {'form': form})

# Editarea unui proiect
def editeaza_proiect(request, pk):
    proiect = get_object_or_404(Proiect, pk=pk)
    if request.method == 'POST':
        form = ProiectForm(request.POST, instance=proiect)
        if form.is_valid():
            form.save()
            return redirect('proiecte_list')
    else:
        form = ProiectForm(instance=proiect)
    return render(request, 'studybuddyapp/editeaza_proiect.html', {'form': form})

# Editarea unui examen
def editeaza_examen(request, pk):
    examen = get_object_or_404(Examen, pk=pk)
    if request.method == 'POST':
        form = ExamenForm(request.POST, instance=examen)
        if form.is_valid():
            form.save()
            return redirect('examene_list')
    else:
        form = ExamenForm(instance=examen)
    return render(request, 'studybuddyapp/editeaza_examen.html', {'form': form})

# Ștergerea unei teme
def sterge_tema(request, pk):
    tema = get_object_or_404(Tema, pk=pk)
    tema.delete()
    return redirect('teme_list')

# Ștergerea unui proiect
def sterge_proiect(request, pk):
    proiect = get_object_or_404(Proiect, pk=pk)
    proiect.delete()
    return redirect('proiecte_list')

# Ștergerea unui examen
def sterge_examen(request, pk):
    examen = get_object_or_404(Examen, pk=pk)
    examen.delete()
    return redirect('examene_list')
