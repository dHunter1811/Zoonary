package com.example.latihan_praktikum_7.presentation.viewmodel;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.latihan_praktikum_7.data.entity.Animal;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AnimalViewModelInstrumentedTest {

    private AnimalViewModel animalViewModel;
    private Context context;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        animalViewModel = new AnimalViewModel(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        animalViewModel = null;
    }

    @Test
    public void testSaveAndLoadAnimalData() throws InterruptedException {
        String testName = "Harimau Sumatera";
        String testSpecies = "Panthera tigris sumatrae";
        String testHabitat = "Hutan hujan Sumatera";

        animalViewModel.saveData(testName, testSpecies, testHabitat);

        // Tunggu LiveData diperbarui
        List<Animal> animalList = getOrAwaitValue(animalViewModel.getAnimalList());

        boolean found = false;
        for (Animal animal : animalList) {
            if (animal.getName().equals(testName) &&
                    animal.getSpecies().equals(testSpecies) &&
                    animal.getDescription().equals(testHabitat)) {
                found = true;
                break;
            }
        }

        assertTrue("Animal yang disimpan harus ditemukan di database.", found);
    }

    @Test
    public void testSetAndGetFavoriteAnimal() throws InterruptedException {
        animalViewModel.saveData("Elang Jawa", "Nisaetus bartelsi", "Pegunungan Jawa");
        List<Animal> allAnimals = getOrAwaitValue(animalViewModel.getAnimalList());

        assertFalse("Daftar hewan tidak boleh kosong", allAnimals.isEmpty());
        int animalId = allAnimals.get(0).getId();

        animalViewModel.setFavorite(animalId, true);
        List<Animal> favorites = getOrAwaitValue(animalViewModel.getFavoriteAnimalList());

        boolean found = false;
        for (Animal fav : favorites) {
            if (fav.getId() == animalId && fav.getIsFavorite() == 1) {
                found = true;
                break;
            }
        }

        assertTrue("Animal harus ditandai sebagai favorit", found);
    }

    @Test
    public void testUpdateAnimalData() throws InterruptedException {
        animalViewModel.saveData("Burung Cenderawasih", "Paradisaea", "Papua");
        List<Animal> list = getOrAwaitValue(animalViewModel.getAnimalList());

        Animal animal = list.get(0);
        int id = animal.getId();

        animalViewModel.updateData(id, "Cenderawasih Updated", "Paradisaea updated", "Hutan Papua");

        List<Animal> updatedList = getOrAwaitValue(animalViewModel.getAnimalList());
        boolean updated = false;

        for (Animal a : updatedList) {
            if (a.getId() == id && a.getName().equals("Cenderawasih Updated")) {
                updated = true;
                break;
            }
        }

        assertTrue("Animal harus diperbarui", updated);
    }

    @Test
    public void testDeleteAnimalData() throws InterruptedException {
        animalViewModel.saveData("Badak Jawa", "Rhinoceros sondaicus", "Taman Nasional Ujung Kulon");
        List<Animal> listBefore = getOrAwaitValue(animalViewModel.getAnimalList());

        assertFalse(listBefore.isEmpty());
        int lastId = listBefore.get(0).getId();

        animalViewModel.deleteData(lastId);
        List<Animal> listAfter = getOrAwaitValue(animalViewModel.getAnimalList());

        boolean deleted = true;
        for (Animal a : listAfter) {
            if (a.getId() == lastId) {
                deleted = false;
                break;
            }
        }

        assertTrue("Animal harus berhasil dihapus", deleted);
    }

    // Fungsi utilitas untuk menunggu LiveData mengembalikan nilai
    private <T> T getOrAwaitValue(final androidx.lifecycle.LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T value) {
                data[0] = value;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        if (!latch.await(2, TimeUnit.SECONDS)) {
            throw new RuntimeException("LiveData value was never set.");
        }

        //noinspection unchecked
        return (T) data[0];
    }
}
